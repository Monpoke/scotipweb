/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package scotip.app.service.operator;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.operator.OperatorDao;
import scotip.app.dto.OperatorDto;
import scotip.app.model.Company;
import scotip.app.model.Operator;
import scotip.app.model.Switchboard;
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
        op.setSkype(OperatorDto.isSkype());
        op.setPassword(OperatorDto.getPassword());

        return dao.registerNewOperator(op);

    }

    @Override
    public List<Operator> getAllOperator(int id) {
        return dao.getAllOperator(company.findById(id));
    }


    @Override
    public List<Operator> getAllOperators(Company company){
        return dao.getAllOperator(company);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    @Override
    public Operator getInitializedOperator(int id) {
        return dao.findInitialized(id);
    }

    @Override
    public void getOperatorsFromSwitchboard(Switchboard switchboard) {
        dao.getOperatorsFromSwitchboard(switchboard);
    }

    @Override
    public Operator findOneByUsername(String name) {
        return dao.findOneByName(name);
    }


}
