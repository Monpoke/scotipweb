package scotip.app.pages.logged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import scotip.app.dto.ModuleUpdateDto;
import scotip.app.exceptions.ModuleModelNotFoundException;
import scotip.app.exceptions.ModuleNotFoundException;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.model.Module;
import scotip.app.model.Switchboard;
import scotip.app.service.module.ModuleService;
import scotip.app.service.moduleModel.ModuleModelService;
import scotip.app.service.sounds.SoundsService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
@SessionAttributes("switchboards")
public class CallsController extends SwitchboardAppController {

    @Autowired
    ModuleModelService moduleModelService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    SwitchboardService switchboardService;

    @Autowired
    SoundsService soundsService;


    @RequestMapping("/u/switchboard/{sid}/calls")
    public String allsCalls(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);

        modelMap.addAttribute("calls", switchboard.getCallLogs());


        return "pages/switchboard/calls";
    }



}
