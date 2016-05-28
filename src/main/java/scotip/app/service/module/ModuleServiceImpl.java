/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package scotip.app.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.model.ModuleDao;
import scotip.app.dto.ModuleDto;
import scotip.app.exceptions.ModuleModelNotFoundException;
import scotip.app.exceptions.OperationException;
import scotip.app.model.Company;
import scotip.app.model.Module;
import scotip.app.model.ModuleModel;
import scotip.app.service.moduleModel.ModuleModelService;
import scotip.app.service.switchboard.SwitchboardService;
import scotip.app.tools.CreateDefaultModule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 13/05/2016.
 */
@Service("modelService")
@Transactional
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleDao moduleDao;

    @Autowired
    ModuleModelService moduleModelService;

    @Autowired
    SwitchboardService switchboardService;

    @Override
    public Module findByIdAndCompany(int id, Company company) {

        return moduleDao.findByIdAndCompany(id, company);
    }

    @Override
    public Module createNewModule(int parentId, String modelSlug, Company currentCompany) throws ModuleModelNotFoundException, OperationException {

        ModuleModel moduleModel = moduleModelService.getEnabledModuleBySlug(modelSlug);

        // ONLY CAN CREATE PLAYBACK
        if(!moduleModel.getSlug().equals("playback")){
           throw new OperationException("Only can create playback.");
        }

        // parent
        Module parentModule = findByIdAndCompany(parentId, currentCompany);

        if(parentModule==null){
            throw new OperationException("unknownParent");
        }

        // CHECK CAN't create model IF
        // parent module is operator or queue
        if(parentModule.getModuleModel().getSlug().matches("queue|operator")){
            throw new OperationException("parentIsLast");
        }


        System.out.println("Size: " + parentModule.getModuleChilds().size());

        Iterator<Module> iterator = parentModule.getModuleChilds().iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().getMid());
        }

        // parent module have one disabled key child
        if(parentModule.getModuleChilds().size() == 1 && parentModule.getModuleChilds().iterator().next().isPhoneKeyDisabled()){
           throw new OperationException("childKeyDisabled");
        }

        // Module
        Module module = CreateDefaultModule.create(moduleModel, parentModule);

        // phone key, have to find the lower one
        Iterator<Module> parentModuleModuleChildren = parentModule.getModuleChilds().iterator();
        setPhoneKey(module, parentModuleModuleChildren);

        // save
        save(module);

        return module;
    }

    @Override
    public Module save(Module module) {
        return moduleDao.saveModule(module);

    }

    /**
     * Removes a module
     *
     * @param moduleId
     * @param currentCompany
     * @return
     */
    @Override
    public Module removeModule(int moduleId, Company currentCompany) throws OperationException {
        Module module = findByIdAndCompany(moduleId, currentCompany);

        if (module.isRootModule()) {
            throw new OperationException("Can't remove root module!");
        } else if (module.getModuleChilds().size() > 0) {
            throw new OperationException("Please remove child modules");
        }

        if(module.getModuleParent() != null) {
            module.getModuleParent().getModuleChilds().remove(module);
        }


        module.getFiles().clear();
        module.getSettings().clear();

        moduleDao.remove(module);


        return module;
    }

    @Override
    public void saveUpdate(Module module, ModuleDto moduleUpdateDto) {
        boolean isRoot = module.isRootModule();


        /**
         * @todo have to check if files exists
         */
        checkDifferentKey(module, moduleUpdateDto);

        String modelSlug = moduleUpdateDto.getModuleType().getSlug();

        // set fields
        if (moduleUpdateDto.isModulePhoneKeyDisable()) {
            module.setPhoneKey(0);
            module.setPhoneKeyDisabled(true);
        } else {
            if(!module.isRootModule()) {
                module.setPhoneKey(moduleUpdateDto.getModulePhoneKey());
            }
            module.setPhoneKeyDisabled(false);
        }

        module.setDescription(moduleUpdateDto.getModuleDescription());
        module.setModuleModel(moduleUpdateDto.getModuleType());


        // OPERATOR
        if (modelSlug.equals("playback")) {
            module.getSettings().put("skip", moduleUpdateDto.isSkip() ? "1" : "0");
        } else {
            module.setOperator(null);
            if(module.getSettings().containsKey("skip")){
                module.getSettings().remove("skip");
            }
        }


        // OPERATOR
        if (modelSlug.equals("operator")) {
            module.setOperator(moduleUpdateDto.getOperator());
        } else {
            module.setOperator(null);
        }


        // QUEUE
        if (modelSlug.equals("queue")) {
            module.setQueue(moduleUpdateDto.getQueue());
        } else {
            module.setQueue(null);
        }

        // QUEUE OR OPERATOR
        if (modelSlug.equals("queue") || modelSlug.equals("operator")) {
            module.setMohGroup(moduleUpdateDto.getMoh());
        } else {
            module.setMohGroup(null);
        }

        // USER INPUT
        if (modelSlug.equals("userinput")) {
            Map<String, String> settings = module.getSettings();
            settings.put("variable", moduleUpdateDto.getVariable());
            settings.put("uri", moduleUpdateDto.getUrlCheck());
            settings.put("numberFormatMin", "" + moduleUpdateDto.getNumberFormatMin());
            settings.put("numberFormatMax", "" + moduleUpdateDto.getNumberFormatMax());

        }

        // FILES
        safeModuleFilesSyntax(moduleUpdateDto.getFiles());
        module.setFiles(moduleUpdateDto.getFiles());

        // RESET ROOT MODULE
        if (isRoot == true) {
            module.setRootModule(true);
            module.setPhoneKeyDisabled(true);
        }


        save(module);

        // reload dialplan
        switchboardService.notifyServerDialplanReload(module.getSwitchboard());
    }

    private void safeModuleFilesSyntax(Map<String, String> files) {
        Iterator<Map.Entry<String, String>> iterator = files.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();

            String val = next.getValue();
            // just remove last &
            if (val.length() > 0 && val.charAt(val.length() - 1) == '&') {
                files.put(next.getKey(), val.substring(0, val.length() - 1));
            }
        }
    }


    private void checkDifferentKey(Module module, ModuleDto moduleUpdateDto) {
        if (module.isRootModule() || moduleUpdateDto.isModulePhoneKeyDisable()) {
            return;
        }

        if (module.getPhoneKey() != moduleUpdateDto.getModulePhoneKey()) {
            // have to switch key
            moduleDao.updatePhoneKeyFrom(module.getSwitchboard(), moduleUpdateDto.getModulePhoneKey(), module.getPhoneKey());
        }

    }

    private void setPhoneKey(Module module, Iterator<Module> parentModuleModuleChildren) throws OperationException {
        List<String> integers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            integers.add("" + i);
        }

        while (parentModuleModuleChildren.hasNext()) {
            Module child = parentModuleModuleChildren.next();
            integers.remove("" + child.getPhoneKey());
        }

        if (integers.size() == 0) {
            throw new OperationException("All phone keys are used");
        }

        // find the lowest one
        integers.sort(new OrderComparator());
        Object[] objects = integers.toArray();
        module.setPhoneKey(Integer.parseInt((String) objects[0]));
    }

    private void removeIfContains(Map<String, Object> kvMap, String key) {
        if (kvMap.containsKey(key)) {
            kvMap.remove(key);
        }
    }
}
