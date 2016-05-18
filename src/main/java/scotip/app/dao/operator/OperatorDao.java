package scotip.app.dao.operator;

import scotip.app.model.Company;
import scotip.app.model.Operator;

import java.util.List;

/**
 * Created by svevia on 18/05/2016.
 */
public interface OperatorDao {

    Operator findById(int id);
    void deleteById(int id);
    Operator registerNewOperator(Operator operator);
    List<Operator> getAllOperator(Company comp);
}
