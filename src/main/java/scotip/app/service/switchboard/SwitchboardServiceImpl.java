package scotip.app.service.switchboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.switchboard.SwitchboardDao;
import scotip.app.dto.SwitchboardDto;
import scotip.app.model.Switchboard;

/**
 * Created by Pierre on 29/04/2016.
 */
@Service("switchboardService")
@Transactional
public class SwitchboardServiceImpl implements SwitchboardService {

    @Autowired
    SwitchboardDao switchboardDao;

    @Override
    public Switchboard registerNewSwitchboard(SwitchboardDto switchboardDto) {

        final Switchboard switchboard = new Switchboard();

        // global vars
        switchboard.setDeleted(false);
        switchboard.setEnabled(true);

        // components
        switchboard.setCompany(switchboardDto.getCompany());
        switchboard.setLine(switchboardDto.getSharedLine());

        // phone
        switchboard.setDescription(switchboardDto.getDescription());
        switchboard.setName(switchboardDto.getName());
        switchboard.setPhoneCodeAccess(switchboardDto.getPhoneCodeAccess());

        return switchboardDao.saveSwitchboard(switchboard);
    }
}
