package scotip.app.service.sounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.sounds.SoundDao;
import scotip.app.model.SoundLibrary;

import java.util.List;

/**
 * Created by Pierre on 14/05/2016.
 */
@Service("soundsService")
@Transactional
public class SoundsServiceImpl implements SoundsService {

    @Autowired
    SoundDao soundDao;


    /**
     * Returns all songs
     * @return
     */
    @Override
    public List<SoundLibrary> getLibrarySounds() {
        return soundDao.getAllLibrary();
    }
}
