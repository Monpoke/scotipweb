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
import scotip.app.dto.ModuleUpdateDto;
import scotip.app.exceptions.OperationException;
import scotip.app.model.Company;
import scotip.app.model.Module;
import scotip.app.model.ModuleModel;
import scotip.app.model.Switchboard;
import scotip.app.service.moduleModel.ModuleModelService;
import scotip.app.service.switchboard.SwitchboardService;
import scotip.app.tools.CreateDefaultModule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    public Module createNewModule(int parentId, String modelSlug, Company currentCompany) throws Exception {

        ModuleModel moduleModel = moduleModelService.getEnabledModuleBySlug(modelSlug);

        // parent
        Module parentModule = findByIdAndCompany(parentId, currentCompany);

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
     * @param parentId
     * @param currentCompany
     * @return
     */
    @Override
    public Module removeModule(int parentId, Company currentCompany) throws OperationException {
        Module module = findByIdAndCompany(parentId, currentCompany);

        if (module.isRootModule()) {
            throw new OperationException("Can't remove root module!");
        } else if (module.getModuleChilds().size() > 0) {
            throw new OperationException("Please remove child modules");
        }

        module.getModuleParent().getModuleChilds().remove(module);
        moduleDao.remove(module);
        return module;
    }

    @Override
    public void saveUpdate(Module module, ModuleUpdateDto moduleUpdateDto) {

        /**
         * @todo have to check if files exists
         */
        checkDifferentKey(module, moduleUpdateDto);

        // set fields
        module.setPhoneKey(moduleUpdateDto.getPhoneKey());
        module.setDescription(moduleUpdateDto.getDescription());

        // library not empty
        if (!moduleUpdateDto.getLibraryFile().isEmpty()) {
            String replace = moduleUpdateDto.getLibraryFile().replace("library/", "");
            module.getSettings().put("file", replace);
        }

        save(module);

        // reload dialplan
        switchboardService.notifyServerDialplanReload(module.getSwitchboard());
    }

    private void checkDifferentKey(Module module, ModuleUpdateDto moduleUpdateDto) {
        if (module.isRootModule()) {
            return;
        }

        if (module.getPhoneKey() != moduleUpdateDto.getPhoneKey()) {
            // have to switch key
            moduleDao.updatePhoneKeyFrom(module.getSwitchboard(), moduleUpdateDto.getPhoneKey(), module.getPhoneKey());
        }

    }

    private void setPhoneKey(Module module, Iterator<Module> parentModuleModuleChildren) throws Exception {
        List<String> integers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            integers.add("" + i);
        }

        while (parentModuleModuleChildren.hasNext()) {
            Module child = parentModuleModuleChildren.next();
            integers.remove("" + child.getPhoneKey());
        }

        if (integers.size() == 0) {
            throw new Exception("All phone keys are used");
        }

        // find the lowest one
        integers.sort(new OrderComparator());
        Object[] objects = integers.toArray();
        module.setPhoneKey(Integer.parseInt((String) objects[0]));
    }
}
