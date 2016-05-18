package scotip.app.dao.operator;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Company;
import scotip.app.model.Operator;

import java.util.List;

/**
 * Created by svevia on 18/05/2016.
 */
@Transactional
@Repository("OperatorDao")
public class OperatorDaoImpl  extends AbstractDao<Integer, Operator> implements OperatorDao{
    @Override
    public Operator findById(int id) {
        return getByKey(id);
    }

    @Override
    public Operator registerNewOperator(Operator operator) {
        operator.setOid((int)getSession().save(operator));
        return operator;
    }

    @Override
    public List<Operator> getAllOperator(Company comp) {
        Query query = getSession().createQuery("from Operator o where o.company = :comp");
        query.setParameter("comp",comp);
        return query.list();

    }
}
