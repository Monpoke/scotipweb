package scotip.app.dao.switchboard;

import scotip.app.model.Switchboard;
import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface SwitchboardDao {
    Switchboard saveSwitchboard(Switchboard switchboard);

    Switchboard get(int sid);

    Switchboard getWithModules(int sid);

    List<Switchboard> getAllSwitchboard();
}
