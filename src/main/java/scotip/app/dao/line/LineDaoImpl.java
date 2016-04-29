package scotip.app.dao.line;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Line;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("lineDao")
@Transactional
public class LineDaoImpl extends AbstractDao<Integer, Line> implements LineDao {

    @Override
    public List<Line> getSharedLines() {
        Criteria shared = createEntityCriteria();
        shared.add(Restrictions.eq("shared", true));
        return shared.list();
    }

    @Override
    public Line findSharedById(int id) {
        Criteria line = createEntityCriteria();
        line
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("shared",true));

        return (Line)line.uniqueResult();
    }

    @Override
    public boolean isAccessCodeAvailable(int code, Line line) {

        Query query = getSession().createQuery("FROM Line AS line INNER JOIN FETCH line.switchboards AS sb WHERE "+
                "sb.phoneCodeAccess = :phoneCodeAccess AND line.lineId = :lineId");
        query
                .setParameter("phoneCodeAccess", code)
                .setParameter("lineId",line.getLineId());


        Object switchboard = query.uniqueResult();

        return switchboard == null;
    }
}
