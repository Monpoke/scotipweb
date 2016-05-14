package scotip.app.pages.logged;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import scotip.app.dto.SwitchboardDto;
import scotip.app.exceptions.NoRootModuleException;
import scotip.app.exceptions.SeveralRootModuleException;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.infos.AlertMessage;
import scotip.app.model.*;
import scotip.app.pages.App;
import scotip.app.service.company.CompanyService;
import scotip.app.service.line.LineService;
import scotip.app.service.sounds.SoundsService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;
import java.util.Iterator;
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

    @Autowired
    private SoundsService soundsService;


    /**
     * Page to choose a board.
     *
     * @param modelMap
     * @return
     */
    @RequestMapping({"/u/switchboard/{sid}/dialplan"})
    protected String dialplanConfig(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);


        // Get Modules
        modelMap.addAttribute("modules", switchboard.getModules());


        Module rootModule = null;
        try {
            rootModule = getRootModule(switchboard.getModules());
        } catch (NoRootModuleException e) {
            e.printStackTrace();
            modelMap.addAttribute("pageSpecialAlert", new AlertMessage("danger",e.getMessage()));
        } catch (SeveralRootModuleException e) {
            e.printStackTrace();
            modelMap.addAttribute("pageSpecialAlert", new AlertMessage("danger",e.getMessage()));
        }
        modelMap.addAttribute("rootModule", rootModule);


        // HAVE TO FIND LIBRARY SOUNDS
        getLibrarySounds(modelMap);

        return ("pages/switchboard/dialplan");
    }

    /**
     * Gets the library sounds
     * @param modelMap
     */
    private void getLibrarySounds(ModelMap modelMap) {
        List<SoundLibrary> librarySounds = soundsService.getLibrarySounds();
        modelMap.put("librarySounds", librarySounds);
    }

    /**
     * Find the root module
     *
     * @param modules
     * @return
     */
    private Module getRootModule(List<Module> modules) throws NoRootModuleException, SeveralRootModuleException {
        Module root = null;

        // module level = 1, parent=null
        for (Iterator<Module> i = modules.iterator(); i.hasNext(); ) {
            Module next = i.next();

            if (next.getModuleParent() == null && next.getModuleLevel() == 1) {
                if (root != null) {
                    throw new SeveralRootModuleException();
                }

                root = next;
            }
        }

        if (root == null) {
            throw new NoRootModuleException();
        }

        return root;
    }


}
