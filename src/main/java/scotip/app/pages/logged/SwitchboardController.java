package scotip.app.pages.logged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import scotip.app.dto.SwitchboardDto;
import scotip.app.exceptions.ModuleModelNotFoundException;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.model.*;
import scotip.app.pages.App;
import scotip.app.service.company.CompanyService;
import scotip.app.service.line.LineService;
import scotip.app.service.module.ModuleService;
import scotip.app.service.moduleModel.ModuleModelService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
@SessionAttributes("switchboards")
public class SwitchboardController extends SwitchboardAppController {

    @Autowired
    ModuleModelService moduleModelService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    SwitchboardService switchboardService;


    @RequestMapping("/u/switchboard/{sid}")
    public String switchboardStats(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);
        modelMap.addAttribute("stats_nbCalls", switchboard.getCallLogs().size());


        return "pages/switchboard/stats";
    }


    @RequestMapping("/u/switchboard/{sid}/operators")
    public String switchboardOperators(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);

        return "pages/switchboard/operators";
    }

    @RequestMapping("/u/switchboard/{sid}/opening")
    public String switchboardOpening(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);

        return "pages/switchboard/opening";
    }


    /*
    ==================================
              CREATE METHODS
    ==================================
     */
    @RequestMapping("/u/module/create/{parent}/{model}")
    @ResponseBody
    public String createModule(@PathVariable("parent") int parentId, @PathVariable("model") String modelSlug, ModelMap modelMap) throws ModuleModelNotFoundException {

        try {
            Module newModule = moduleService.createNewModule(parentId, modelSlug, getCurrentCompany());

            // reload dialplan
            switchboardService.notifyServerDialplanReload(newModule.getSwitchboard());

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/u/module/delete/{parent}")
    @ResponseBody
    public String deleteModule(@PathVariable("parent") int parentId, ModelMap modelMap) throws ModuleModelNotFoundException {

        try {
            Module oldModule = moduleService.removeModule(parentId, getCurrentCompany());
            // reload dialplan
            switchboardService.notifyServerDialplanReload(oldModule.getSwitchboard());

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
