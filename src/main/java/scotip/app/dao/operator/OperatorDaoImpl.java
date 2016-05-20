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

package scotip.app.dao.operator;

import org.hibernate.Hibernate;
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
    public Operator findInitialized(int id){
        Operator byId = findById(id);
        if(byId!=null){
            Hibernate.initialize(byId.getCompany());
        }

        return byId;
    }

    @Override
    public void deleteById(int id) {delete(getByKey(id));}

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
