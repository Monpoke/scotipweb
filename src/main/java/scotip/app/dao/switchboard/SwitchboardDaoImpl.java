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

package scotip.app.dao.switchboard;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Company;
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

    @Override
    public void initQueues(Switchboard switchboard) {
        Session session = getSession();
        Hibernate.initialize(switchboard.getQueues());
        session.close();
    }

    @Override
    public List<Switchboard> getAllSwitchboardFromCompany(Company currentCompany) {
        return createEntityCriteria().add(Restrictions.eq("company",currentCompany)).list();
    }
}
