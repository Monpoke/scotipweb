package scotip.app.service.module;

import scotip.app.model.Company;
import scotip.app.model.Module;

/**
 * Created by Pierre on 13/05/2016.
 */
public interface ModuleService {
    Module findByIdAndCompany(int parentId, Company currentCompany);

    Module createNewModule(int parentId, String modelSlug, Company currentCompany) throws Exception;

    Module save(Module module);
}
