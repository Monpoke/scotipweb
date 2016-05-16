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

package scotip.app.dao.model;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Company;
import scotip.app.model.Module;
import scotip.app.model.Switchboard;

/**
 * Created by Pierre on 13/05/2016.
 */
@Repository("moduleDao")
@Transactional
public class ModuleDaoImpl extends AbstractDao<Integer, Module> implements ModuleDao {
    @Override
    public Module findByIdAndCompany(int parentId, Company company) {
        Module byKey = getByKey(parentId);
        if(byKey == null || byKey.getSwitchboard().getCompany().getId() != company.getId()){
            return null;
        }
        // need them
        Hibernate.initialize(byKey.getModuleChilds());
        return byKey;
    }

    @Override
    public Module saveModule(Module module) {
        getSession().saveOrUpdate(module);
        return module;
    }

    @Override
    public void remove(Module module) {
        getSession().delete(module);
    }


    @Override
    public void updatePhoneKeyFrom(Switchboard switchboard, int from, int to) {
        Query query = getSession().createQuery("UPDATE Module SET phoneKey = :to WHERE phoneKey = :fro AND switchboard = :switchboard");
        query.setParameter("to",to);
        query.setParameter("fro",from);
        query.setParameter("switchboard",switchboard);
        query.executeUpdate();
    }
}
