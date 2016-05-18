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

package scotip.app.service.sounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.sounds.MOHGroupDao;
import scotip.app.dao.sounds.SoundDao;
import scotip.app.dto.MohGroupAdd;
import scotip.app.model.Company;
import scotip.app.model.MohGroup;
import scotip.app.model.SoundLibrary;
import scotip.app.model.Switchboard;

import java.util.List;

/**
 * Created by Pierre on 14/05/2016.
 */
@Service("soundsService")
@Transactional
public class SoundsServiceImpl implements SoundsService {

    @Autowired
    SoundDao soundDao;

    @Autowired
    MOHGroupDao mohGroupDao;


    /**
     * Returns all songs
     * @return
     */
    @Override
    public List<SoundLibrary> getLibrarySounds() {
        return soundDao.getAllLibrary();
    }

    @Override
    public List<SoundLibrary> getSoundsFromList(String[] slugs) {
        return soundDao.getSoundsFromList(slugs);
    }


    /**
     * Saves group
     * @param mohGroupAdd
     * @param switchboard
     */
    @Override
    public void saveMOHGroup(MohGroupAdd mohGroupAdd, Switchboard switchboard) {

        MohGroup mohGroup = new MohGroup(switchboard);
        mohGroup.setName(mohGroupAdd.getGroupName());

        mohGroupDao.saveGroup(mohGroup);
    }

    @Override
    public void removeMOHGroup(Switchboard switchboard, int mid) {
        mohGroupDao.removeMOHGroup(switchboard, mid);
    }


}
