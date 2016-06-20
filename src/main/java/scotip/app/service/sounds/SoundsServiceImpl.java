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

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.config.MainConfig;
import scotip.app.dao.sounds.MOHFileDao;
import scotip.app.dao.sounds.MOHGroupDao;
import scotip.app.dao.sounds.SoundDao;
import scotip.app.dto.MohGroupAdd;
import scotip.app.model.*;

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

    @Autowired
    MOHFileDao mohFileDao;


    /**
     * Returns all songs
     *
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
     *
     * @param mohGroupAdd
     * @param switchboard
     */
    @Override
    public void saveMOHGroup(MohGroupAdd mohGroupAdd, Switchboard switchboard) {

        MohGroup mohGroup = new MohGroup(switchboard);
        mohGroup.setName(mohGroupAdd.getGroupName());

        mohGroupDao.saveGroup(mohGroup);

        this.notifyServerReload(switchboard.getCompany());
    }

    @Override
    public void removeMOHGroup(Switchboard switchboard, int mid) {
        mohGroupDao.removeMOHGroup(switchboard, mid);

        // server reload
        this.notifyServerReload(switchboard.getCompany());
    }

    @Override
    public MohGroup getMohGroupWithIdAndSwitchboard(int mid, int sid) {
        MohGroup mohGroup = mohGroupDao.findById(mid);
        if (mohGroup == null || mohGroup.getSwitchboard().getSid() != sid) {
            return null;
        }

        return mohGroup;
    }

    @Override
    public MohGroup getMohGroupWithIdAndSwitchboardAndCompany(int mid, int sid, int companyId) {
        MohGroup mohGroup = getMohGroupWithIdAndSwitchboard(mid,sid);
        if(mohGroup==null){
            return null;
        }

        Hibernate.initialize(mohGroup.getSwitchboard().getCompany());
        if(mohGroup.getSwitchboard().getCompany().getId() != companyId){
            return null;
        }

        return mohGroup;
    }

    @Override
    public MohGroup getMohGroup(int mid) {
        return mohGroupDao.findById(mid);
    }

    @Override
    public SoundLibrary getSoundSlug(String slug) {
        return soundDao.getFromSlug(slug);
    }

    @Override
    public void notifyServerReload(Company company) {

        Unirest.get(MainConfig.NODESPAS_URL + "/moh/" + company.getId()).asJsonAsync(new Callback<JsonNode>() {
            @Override
            public void completed(HttpResponse<JsonNode> httpResponse) {
            }
            @Override
            public void failed(UnirestException e) {
                e.printStackTrace();
            }
            @Override
            public void cancelled() {
            }
        });
    }

    @Override
    public int saveMohFILE(MohFile mohFile) {
        return mohFileDao.saveMohFILE(mohFile);
    }



    @Override
    public MohFile getMohFileWithIdAndSwitchboard(int mid, int sid) {
        MohFile mohFile = mohFileDao.findById(mid);
        if (mohFile == null || mohFile.getGroup().getSwitchboard().getSid() != sid) {
            return null;
        }

        return mohFile;
    }


    @Override
    public MohFile getMohFileWithIdAndSwitchboardAndCompany(int mohid, int switchboardId, int companyId) {
        MohFile mohGroupWithIdAndSwitchboard = getMohFileWithIdAndSwitchboard(mohid, switchboardId);
        if(mohGroupWithIdAndSwitchboard==null){
            return null;
        }

        if(mohGroupWithIdAndSwitchboard.getGroup().getSwitchboard().getCompany().getId() != companyId){
            return null;
        }
        return mohGroupWithIdAndSwitchboard;
    }

    @Override
    public void removeMOHFile(MohFile mohFile) {
        mohFileDao.removeFile(mohFile);
    }

    @Override
    public List<MohFile> getMOHFilesFromGroup(MohGroup mohGroup) {
        return mohFileDao.getFilesFromGroup(mohGroup);
    }


}
