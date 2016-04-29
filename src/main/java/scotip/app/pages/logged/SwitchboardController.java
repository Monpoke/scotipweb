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
import scotip.app.pages.App;
import scotip.app.service.company.CompanyService;
import scotip.app.service.line.LineService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
@SessionAttributes("lines")
public class SwitchboardController extends App {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LineService lineService;


    @RequestMapping({"/u/switch", "/u/switch/choose"})
    protected String chooseABoard(ModelMap modelMap) {

        Company currentCompany = getCurrentCompany();
        if (currentCompany == null) {
            throw new AccessDeniedException("You should be logged.");
        }

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

        Company currentCompany = getCurrentCompany();
        if (currentCompany == null) {
            throw new AccessDeniedException("You should be logged.");
        }


        return ("pages/switchboard/new");
    }


    @RequestMapping(value = "/u/switch/new", method = RequestMethod.POST)
    public String createProcess(@ModelAttribute("switchboard") @Valid SwitchboardDto switchboardDto, BindingResult bindingResult, ModelMap modelMap){

        if(bindingResult.hasErrors()){
            System.out.println("DES ERREURS SONT PRESENTES");
        }


        return ("pages/switchboard/new");
    }






    /**
     * Puts in all these pages the shared numbers.
     * @return
     */
    @ModelAttribute("sharedLines")
    public List<Line> getSharedLines() {
        return lineService.getSharedLines();
    }


}
