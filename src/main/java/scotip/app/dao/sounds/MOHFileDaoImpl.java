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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.AbstractDao;
import scotip.app.model.MohFile;
import scotip.app.model.MohGroup;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Pierre on 28/05/2016.
 */
@Repository("mohFileDao")
@Transactional
public class MOHFileDaoImpl extends AbstractDao<Integer, MohFile> implements MOHFileDao {
    @Override
    public int saveMohFILE(MohFile mohFile) {
        return saveFile(mohFile).getSoundId();
    }


    @Override
    public MohFile saveFile(MohFile mohFile) {
        Session session = getSession();
        mohFile.setSoundId((int) session.save(mohFile));
        session.flush();
        return mohFile;
    }

    @Override
    public MohFile findById(int mid) {
        return (MohFile) getByKey(mid);
    }

    @Override
    public void removeFile(MohFile mohFile) {
        getSession().delete(mohFile);
    }

    @Override
    public List<MohFile> getFilesFromGroup(MohGroup mohGroup) {
        Query query = getSession().createQuery("from MohFile WHERE group = :group");
        query.setParameter("group", mohGroup);
        return query.list();
    }
}
