package scotip.app.dao.switchboard;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Switchboard;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("switchboardDao")
@Transactional
public class SwitchboardDaoImpl extends AbstractDao<Integer, Switchboard>  implements SwitchboardDao {


    /**
     * Save to switchboard
     * @param switchboard
     * @return
     */
    @Override
    public Switchboard saveSwitchboard(Switchboard switchboard) {
        switchboard.setSid((int)getSession().save(switchboard));
        return switchboard;
    }

    @Override
    public Switchboard get(int sid) {
        return getByKey(sid);
    }

    @Override
    public Switchboard getWithModules(int sid) {
        Switchboard switchboard = get(sid);
        Hibernate.initialize(switchboard.getModules());
        Hibernate.initialize(switchboard.getCallLogs());
        return switchboard;
    }

    public List<Switchboard> getAllSwitchboard(){
        return getSession().createCriteria(Switchboard.class).list();
    }
}
