package scotip.app.service.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.operator.OperatorDao;
import scotip.app.dto.OperatorDto;
import scotip.app.model.Operator;
import scotip.app.service.company.CompanyService;

import java.util.List;

/**
 * Created by svevia on 18/05/2016.
 */
@Service("OperatorService")
@Transactional
public class OperatorServiceImpl implements OperatorService{

    @Autowired
    private OperatorDao dao;

    @Autowired
    private CompanyService company;

    @Override
    public Operator findById(int id) {
       return dao.findById(id);
    }

    @Override
    public Operator registerNewOperator(OperatorDto OperatorDto) {

        Operator op = new Operator();
        op.setCompany(OperatorDto.getCompany());
        op.setName(OperatorDto.getName());
        System.out.println(op.getName());
        op.setPassword(OperatorDto.getPassword());

        return dao.registerNewOperator(op);

    }

    @Override
    public List<Operator> getAllOperator(int id) {
        return dao.getAllOperator(company.findById(id));
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }


}
