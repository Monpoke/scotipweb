package scotip.app.dao.sounds;

import scotip.app.model.SoundLibrary;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface SoundDao {
    List<SoundLibrary> getAllLibrary();

    List<SoundLibrary> getSoundsFromList(String[] slugs);
}
