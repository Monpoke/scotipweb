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

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Queue;
import scotip.app.model.Switchboard;

import java.util.List;

@Transactional
@Repository("QueueDao")
public class QueueDaoImpl extends AbstractDao<Integer, Queue> implements QueueDao {


    @Override
    public List<Queue> getQueuesFromSwitchboard(Switchboard switchboard) {
        Query query = getSession().createQuery("from Queue WHERE switchboard = :switchboard");
        query.setParameter("switchboard", switchboard);
        return query.list();
    }

    @Override
    public void saveQueue(Queue queue) {
        getSession().save(queue);
    }

    @Override
    public void removeQueue(Queue queue) {
        getSession().delete(queue);
    }

    @Override
    public Queue getQueueWithSwitchboardAndId(Switchboard switchboard, int qid) {
        Query query = getSession().createQuery("from Queue WHERE switchboard = :switchboard AND qid = :qid");
        query.setParameter("switchboard",switchboard);
        query.setParameter("qid",qid);
        return (Queue) query.uniqueResult();
    }

    @Override
    public void updateQueue(Queue queue) {
        getSession().update(queue);
    }
}
