package scotip.app.dao.model;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Module;
import scotip.app.model.ModuleModel;
import scotip.app.model.Switchboard;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("modelDao")
@Transactional
public class ModuleModelDaoImpl extends AbstractDao<Integer, ModuleModel>  implements ModuleModelDao {

}
