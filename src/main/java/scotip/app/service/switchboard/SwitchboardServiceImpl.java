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

package scotip.app.service.switchboard;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.config.MainConfig;
import scotip.app.dao.switchboard.SwitchboardDao;
import scotip.app.dto.SwitchboardDto;
import scotip.app.model.Company;
import scotip.app.model.Module;
import scotip.app.model.ModuleModel;
import scotip.app.model.Switchboard;
import scotip.app.service.moduleModel.ModuleModelService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 29/04/2016.
 */
@Service("switchboardService")
@Transactional
public class SwitchboardServiceImpl implements SwitchboardService {

    @Autowired
    SwitchboardDao switchboardDao;

    @Autowired
    ModuleModelService moduleModelService;


    @Override
    public Switchboard registerNewSwitchboard(SwitchboardDto switchboardDto) {

        final Switchboard switchboard = new Switchboard();

        // global vars
        switchboard.setDeleted(false);
        switchboard.setEnabled(true);

        // components
        switchboard.setCompany(switchboardDto.getCompany());
        switchboard.setLine(switchboardDto.getSharedLine());

        // phone
        switchboard.setDescription(switchboardDto.getDescription());
        switchboard.setName(switchboardDto.getName());
        switchboard.setPhoneCodeAccess(switchboardDto.getPhoneCodeAccess());


        /**
         * CREATE DEFAULT DIALPLAN
         */
        createDefaultDialplan(switchboard);


        return switchboardDao.saveSwitchboard(switchboard);
    }


    /**
     * Create a default dialplan.
     *
     * @param switchboard
     */
    private void createDefaultDialplan(Switchboard switchboard) {
        /**
         * Have to get some modules from databases.
         */
        Map<String, ModuleModel> modulesBySlugsAndMap = moduleModelService.getModulesBySlugsAndMap(new String[]{"playback", "read"});

        /**
         * DIALPLAN MODULES
         */
        Module rootModule = new Module(switchboard);
        rootModule.setPhoneKey(-1);
        rootModule.setModuleLevel(1);
        rootModule.setDescription("My first module.");
        rootModule.setModuleModel(modulesBySlugsAndMap.get("playback"));
        rootModule.getSettings().put("message", "library/hello-world");

        Module module_key1 = new Module(switchboard);
        module_key1.setPhoneKey(1);
        module_key1.setModuleLevel(2);
        module_key1.setDescription("My second module.");
        module_key1.setModuleModel(modulesBySlugsAndMap.get("playback"));
        module_key1.getSettings().put("message", "library/hello-world");
        rootModule.addChildModule(module_key1);


        Module module_key3 = new Module(switchboard);
        module_key3.setPhoneKey(2);
        module_key3.setModuleLevel(2);
        module_key3.setDescription("My third module.");
        module_key3.setModuleModel(modulesBySlugsAndMap.get("playback"));
        module_key3.getSettings().put("message", "library/hello-world");
        rootModule.addChildModule(module_key3);


        // register module root
        switchboard.getModules().add(rootModule);
    }


    @Override
    public Switchboard getSwitchboardWithIdAndCompany(int sid, Company company) {
        if (company == null) {
            return null;
        }

        // REQUEST COMPANIES ARE SAME
        Switchboard switchboard = switchboardDao.getWithModules(sid);
        Hibernate.initialize(switchboard.getMohGroups());
        Hibernate.initialize(switchboard.getQueues());

        if (switchboard != null && switchboard.getCompany().getId() != company.getId()) {
            switchboard = null;
        }

        return switchboard;
    }

    @Override
    public void notifyServerDialplanReload(Switchboard switchboard) {
        Unirest.get(MainConfig.NODESPAS_URL + "/switchboards/" + switchboard.getSid()).asJsonAsync(new Callback<JsonNode>() {
            @Override
            public void completed(HttpResponse<JsonNode> httpResponse) {
            }
            @Override
            public void failed(UnirestException e) {
                e.printStackTrace();
            }
            @Override
            public void cancelled() {
            }
        });
    }


    public List<Switchboard> getAllSwitchboard(){
        return switchboardDao.getAllSwitchboard();
    }

    @Override
    public List<Switchboard> getAllSwitchboardFromCompany(Company currentCompany) {
        return switchboardDao.getAllSwitchboardFromCompany(currentCompany);
    }

}
