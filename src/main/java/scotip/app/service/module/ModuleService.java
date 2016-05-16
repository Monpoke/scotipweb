package scotip.app.service.module;

import scotip.app.dto.ModuleUpdateDto;
import scotip.app.exceptions.OperationException;
import scotip.app.model.Company;
import scotip.app.model.Module;

/**
 * Created by Pierre on 13/05/2016.
 */
public interface ModuleService {
    Module findByIdAndCompany(int id, Company currentCompany);

    Module createNewModule(int parentId, String modelSlug, Company currentCompany) throws Exception;

    Module save(Module module);

    Module removeModule(int parentId, Company currentCompany) throws OperationException;

    void saveUpdate(Module module, ModuleUpdateDto moduleUpdateDto);
}
