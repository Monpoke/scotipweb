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

package scotip.app.dao.sounds;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.SoundLibrary;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
@Repository("soundDao")
@Transactional
public class SoundDaoImpl extends AbstractDao<Integer, SoundLibrary>  implements SoundDao {


    @Override
    public List<SoundLibrary> getAllLibrary() {
        Query query = getSession().createQuery("from SoundLibrary");
        query.setCacheable(true);
        return query.list();
    }

    @Override
    public List<SoundLibrary> getSoundsFromList(String[] slugs) {
        Query query = getSession().createQuery("from SoundLibrary WHERE slug IN :slugs");
        query.setParameterList("slugs", slugs);
        System.out.println(query.getQueryString());
        return query.list();
    }
}
