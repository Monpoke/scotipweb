package scotip.app.dao.switchboard;

import scotip.app.model.Switchboard;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface SwitchboardDao {
    Switchboard saveSwitchboard(Switchboard switchboard);

    Switchboard get(int sid);
}
