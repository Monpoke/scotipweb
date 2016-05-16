package scotip.app.service.sounds;

import scotip.app.model.SoundLibrary;

import java.util.List;

/**
 * Created by Pierre on 14/05/2016.
 */
public interface SoundsService {

    List<SoundLibrary> getLibrarySounds();

    List<SoundLibrary> getSoundsFromList(String[] slugs);
}
