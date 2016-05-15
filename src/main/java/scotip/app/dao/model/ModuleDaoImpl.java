package scotip.app.dao.model;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Company;
import scotip.app.model.Module;

/**
 * Created by Pierre on 13/05/2016.
 */
@Repository("moduleDao")
@Transactional
public class ModuleDaoImpl extends AbstractDao<Integer, Module> implements ModuleDao {
    @Override
    public Module findByIdAndCompany(int parentId, Company company) {
        Module byKey = getByKey(parentId);
        if(byKey == null || byKey.getSwitchboard().getCompany().getId() != company.getId()){
            return null;
        }
        // need them
        Hibernate.initialize(byKey.getModuleChilds());
        return byKey;
    }

    @Override
    public Module saveModule(Module module) {
        getSession().saveOrUpdate(module);
        return module;
    }

    @Override
    public void remove(Module module) {
        getSession().delete(module);
    }
}
