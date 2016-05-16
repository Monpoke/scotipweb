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

package scotip.app.service.moduleModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.model.ModuleModelDao;
import scotip.app.dao.switchboard.SwitchboardDao;
import scotip.app.dto.SwitchboardDto;
import scotip.app.exceptions.ModuleModelNotFoundException;
import scotip.app.model.Company;
import scotip.app.model.ModuleModel;
import scotip.app.model.Switchboard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 29/04/2016.
 */
@Service("modelModelService")
@Transactional
public class ModuleModelServiceImpl implements ModuleModelService {

    @Autowired
    ModuleModelDao moduleModelDao;



    /**
     * Get Modules by slugs with list return
     * @param slugs
     * @return
     */
    public Map<String,ModuleModel> getModulesBySlugsAndMap(String[] slugs){
        HashMap<String, ModuleModel> stringModuleModelHashMap = new HashMap<>();

        List list = moduleModelDao.getModelsWithSlug(slugs);
        for(Iterator<ModuleModel> i = list.iterator(); i.hasNext();){
            ModuleModel cModuleModel = i.next();
            stringModuleModelHashMap.put(cModuleModel.getSlug(), cModuleModel);
        }

        return stringModuleModelHashMap;
    }

    @Override
    public ModuleModel getEnabledModuleBySlug(String slug) throws ModuleModelNotFoundException {
        ModuleModel modelBySlug = moduleModelDao.getModelBySlug(slug);
        if(modelBySlug==null || !modelBySlug.isEnabled()){
            throw new ModuleModelNotFoundException();
        }

        return modelBySlug;
    }

}
