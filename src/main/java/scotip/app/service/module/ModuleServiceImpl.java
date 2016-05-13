package scotip.app.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.model.ModuleDao;
import scotip.app.exceptions.OperationException;
import scotip.app.model.Company;
import scotip.app.model.Module;
import scotip.app.model.ModuleModel;
import scotip.app.service.moduleModel.ModuleModelService;
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

    @Override
    public Module findByIdAndCompany(int parentId, Company company) {

        return moduleDao.findByIdAndCompany(parentId, company);
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

        if(module.isRootModule()){
            throw new OperationException("Can't remove root module!");
        } else if(module.getModuleChilds().size()>0){
            throw new OperationException("Please remove child modules");
        }

        module.getModuleParent().getModuleChilds().remove(module);
        moduleDao.remove(module);
        return module;
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
