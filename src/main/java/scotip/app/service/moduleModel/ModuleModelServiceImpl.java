package scotip.app.service.moduleModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.model.ModuleModelDao;
import scotip.app.dao.switchboard.SwitchboardDao;
import scotip.app.dto.SwitchboardDto;
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

}
