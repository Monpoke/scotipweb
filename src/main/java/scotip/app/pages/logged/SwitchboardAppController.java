package scotip.app.pages.logged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import scotip.app.model.Switchboard;
import scotip.app.pages.App;
import scotip.app.service.switchboard.SwitchboardService;

/**
 * Created by Pierre on 18/04/2016.
 */
public abstract class SwitchboardAppController extends App {

    @Autowired
    protected SwitchboardService switchboardService;



    protected Switchboard getSwitchboard(int sid) {
        return switchboardService.getSwitchboardWithIdAndCompany(sid,getCurrentCompany());
    }
}
