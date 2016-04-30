package scotip.app.pages.logged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import scotip.app.dto.SwitchboardDto;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.model.Company;
import scotip.app.model.Line;
import scotip.app.model.Switchboard;
import scotip.app.pages.App;
import scotip.app.service.company.CompanyService;
import scotip.app.service.line.LineService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
@SessionAttributes("dialplan")
public class DialplanController extends SwitchboardAppController {

    /**
     * SERVICES
     */
    @Autowired
    private CompanyService companyService;

    @Autowired
    private LineService lineService;

    @Autowired
    private SwitchboardService switchboardService;


    /**
     * Page to choose a board.
     *
     * @param modelMap
     * @return
     */
    @RequestMapping({"/u/switchboard/{sid}/dialplan"})
    protected String dialplanConfig(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if(switchboard==null){
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard",switchboard);

        return ("pages/switchboard/dialplan");
    }


}
