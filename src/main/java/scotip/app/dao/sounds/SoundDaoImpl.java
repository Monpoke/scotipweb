package scotip.app.dao.sounds;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.SoundLibrary;
import scotip.app.model.Switchboard;
import scotip.app.service.sounds.SoundsService;

import javax.persistence.Entity;
import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("soundDao")
@Transactional
public class SoundDaoImpl extends AbstractDao<Integer, SoundLibrary>  implements SoundDao {


    @Override
    public List<SoundLibrary> getAllLibrary() {
        Query query = getSession().createQuery("from SoundLibrary");
        query.setCacheable(true);
        return query.list();
    }
}
