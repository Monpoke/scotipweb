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

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.CallLog;
import scotip.app.model.Company;
import scotip.app.model.Switchboard;

import java.util.*;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("callDao")
@Transactional
public class CallDaoImpl extends AbstractDao<Integer, CallLog> implements CallDao {

    @Override
    public List getIncomingCallsByMonth(Company company) {

        Query query = getSession().createQuery("select count(*) as callsNb, month(timestamp) as month from CallLog cl WHERE cl.switchboard.company = :cmp " +
                " AND cast(cl.timestamp as date) between :startDate AND :endDate" +
                " group by month(cl.timestamp)");

        query.setParameter("cmp", company);

        // months
        GregorianCalendar endDate = new GregorianCalendar();
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(Calendar.MONTH, -6);

        query.setParameter("startDate",startDate.getTime());
        query.setParameter("endDate", endDate.getTime());

        System.out.println(query.getQueryString());
        return query.list();
    }
}
