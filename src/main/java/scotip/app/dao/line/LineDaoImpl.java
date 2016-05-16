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

package scotip.app.dao.line;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.Line;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("lineDao")
@Transactional
public class LineDaoImpl extends AbstractDao<Integer, Line> implements LineDao {

    @Override
    public List<Line> getSharedLines() {
        Criteria shared = createEntityCriteria();
        shared.add(Restrictions.eq("shared", true));
        return shared.list();
    }

    @Override
    public Line findSharedById(int id) {
        Criteria line = createEntityCriteria();
        line
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("shared",true));

        return (Line)line.uniqueResult();
    }

    @Override
    public boolean isAccessCodeAvailable(int code, Line line) {

        Query query = getSession().createQuery("FROM Line AS line INNER JOIN FETCH line.switchboards AS sb WHERE "+
                "sb.phoneCodeAccess = :phoneCodeAccess AND line.lineId = :lineId");
        query
                .setParameter("phoneCodeAccess", code)
                .setParameter("lineId",line.getLineId());


        Object switchboard = query.uniqueResult();

        return switchboard == null;
    }
}
