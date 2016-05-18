package scotip.app.service.operator;

import scotip.app.dto.OperatorDto;
import scotip.app.model.Operator;

import java.util.List;

/**
 * Created by svevia on 18/05/2016.
 */
public interface OperatorService {

    Operator findById(int id);

    Operator registerNewOperator(OperatorDto OperatorDto);

    List<Operator> getAllOperator(int id);

    void deleteById(int id);
}
