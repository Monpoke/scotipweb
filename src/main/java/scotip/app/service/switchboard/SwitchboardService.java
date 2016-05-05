package scotip.app.service.switchboard;

import scotip.app.dto.SwitchboardDto;
import scotip.app.model.Company;
import scotip.app.model.Switchboard;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface SwitchboardService {
    Switchboard registerNewSwitchboard(SwitchboardDto switchboardDto);

    Switchboard getSwitchboardWithIdAndCompany(int sid, Company company);

    void notifyServerDialplanReload(Switchboard switchboard);

    List<Switchboard> getAllSwitchboard();
}
