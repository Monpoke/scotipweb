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

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.infos.IncomingCallsLogsWeek;
import scotip.app.model.CallLog;
import scotip.app.model.Company;

import java.util.*;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("callDao")
@Transactional
public class CallDaoImpl extends AbstractDao<Integer, CallLog> implements CallDao {

    @Override
    public List<IncomingCallsLogsWeek> getIncomingCallsByWeek(Company company) {

        Query query = getSession().createQuery("select count(*) as callsNb, day(timestamp) as day, month(timestamp) as month from CallLog cl WHERE cl.switchboard.company = :cmp " +
                " AND cast(cl.timestamp as date) between :startDate AND :endDate" +
                " group by day(cl.timestamp), month(cl.timestamp)");

        query.setParameter("cmp", company);

        // months
        GregorianCalendar endDate = new GregorianCalendar();
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.add(Calendar.DAY_OF_MONTH, -6);

        query.setParameter("startDate",startDate.getTime());
        query.setParameter("endDate", endDate.getTime());
        List list = query.list();

        List<IncomingCallsLogsWeek> callsLogsWeeks = new ArrayList<>();

        System.out.println(query.getQueryString());
        System.out.println("ResultSQL: "+ list.size());

        // associate
        for (Object object : list) {
            Object[] result = (Object[]) object;
            IncomingCallsLogsWeek incoming = new IncomingCallsLogsWeek();
            incoming.setTotal(((Long) result[0]));
            incoming.setDay(((Integer) result[1]));
            incoming.setMonth(((Integer) result[2]));
            callsLogsWeeks.add(incoming);
        }

        return callsLogsWeeks;
    }
}
