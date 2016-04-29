package scotip.app.pages.logged;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import scotip.app.dto.SwitchboardDto;
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
@SessionAttributes("lines")
public class SwitchboardController extends App {

    /**
     * CONSTANTS
     */
    public final static int MAX_SWITCHBOARDS = 3;


    @ModelAttribute("MAX")
    private int getMaximumSwitchboards(){
        return MAX_SWITCHBOARDS;
    }

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
    @RequestMapping({"/u/switch", "/u/switch/choose"})
    protected String chooseABoard(ModelMap modelMap) {

        Company currentCompany = getCurrentCompany();
        if (currentCompany == null) {
            throw new AccessDeniedException("You should be logged.");
        }

        // Refresh company
        companyService.refresh(currentCompany);


        modelMap.addAttribute("companySwitchboards", currentCompany.getSwitchboards());


        return ("pages/switchboard/choose");
    }


    /**
     * Create a new switchboardDto.
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/u/switch/new", method = RequestMethod.GET)
    public String create(ModelMap modelMap) {

        if(!canCreateSwitchboard()){
            return "redirect:/u/switch?new=maxReached";
        }

        Company currentCompany = getCurrentCompany();
        if (currentCompany == null) {
            throw new AccessDeniedException("You should be logged.");
        }


        return ("pages/switchboard/new");
    }


    @RequestMapping(value = "/u/switch/new", method = RequestMethod.POST)
    public String createProcess(@ModelAttribute("switchboard") @Valid SwitchboardDto switchboardDto, BindingResult bindingResult, ModelMap modelMap) {
        if(!canCreateSwitchboard()){
            return "redirect:/u/switch?new=maxReached";
        }


        // NO ERRORS, CAN SAVE
        if (!bindingResult.hasErrors()) {

            Switchboard switchboard = null;
            switchboard = createNewSwitchboard(switchboardDto, bindingResult);

            // OK
            if (switchboard != null) {
                return "redirect:/u/switch?new=switch";
            }
        }

        return ("pages/switchboard/new");
    }

    /**
     * Creates a new switchboard.
     *
     * @param switchboardDto
     * @param bindingResult
     * @return
     */
    private Switchboard createNewSwitchboard(SwitchboardDto switchboardDto, BindingResult bindingResult) {
        // Set user
        switchboardDto.setCompany(getCurrentCompany());

        // try to register
        return switchboardService.registerNewSwitchboard(switchboardDto);
    }


    /**
     * Puts in all these pages the shared numbers.
     *
     * @return
     */
    @ModelAttribute("sharedLines")
    public List<Line> getSharedLines() {
        return lineService.getSharedLines();
    }

    /**
     * Returns true if current company can create more switchboard.
     * @return
     */
    public boolean canCreateSwitchboard(){
        Company currentCompany = getCurrentCompany();
        if(currentCompany.getSwitchboards().size() >= getMaximumSwitchboards()){
            return false;
        }
        return true;
    }

}
