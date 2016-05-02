package scotip.app.dao.model;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.ModuleModel;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("moduleModelDao")
@Transactional
public class ModuleModelDaoImpl extends AbstractDao<Integer, ModuleModel>  implements ModuleModelDao {

    @Override
    public ModuleModel getModelBySlug(String slug) {
        Criteria entityCriteria = createEntityCriteria();
        entityCriteria.add(Restrictions.eq("slug",slug));

        return (ModuleModel) entityCriteria.uniqueResult();
    }

    @Override
    public List<ModuleModel> getModelsWithSlug(String[] slugs) {
        Query query = getSession().createQuery("FROM ModuleModel WHERE slug IN (:slugs)");
        query.setParameterList("slugs", slugs);
        return query.list();
    }
}
