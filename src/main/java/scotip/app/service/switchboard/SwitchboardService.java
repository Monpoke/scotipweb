package scotip.app.service.switchboard;

import scotip.app.dto.SwitchboardDto;
import scotip.app.model.Switchboard;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface SwitchboardService {
    Switchboard registerNewSwitchboard(SwitchboardDto switchboardDto);
}
