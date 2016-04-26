package scotip.app.pages.visitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import scotip.app.dto.CompanyDto;
import scotip.app.exceptions.EmailExistsException;
import scotip.app.model.Company;
import scotip.app.service.CompanyService;

import javax.validation.Valid;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class RegistrationController {

    /**
     * REQUEST USER SERVICE
     */
    @Autowired
    private CompanyService service;


    /**
     * Registration page
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(WebRequest request, Model model) {

        CompanyDto companyDto = new CompanyDto();
        model.addAttribute("company", companyDto);


        return "pages/nonLogged/registration";
    }


    /**
     * The action called
     * @param accountDto
     * @param result
     * @param request
     * @param errors
     * @return
     */
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ModelAndView registerCompanyAccount(
            @ModelAttribute("company") @Valid CompanyDto accountDto,
            BindingResult result, WebRequest request, Errors errors) {

        Company registered = new Company();
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }


        // REST HERE
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        } else {
            return new ModelAndView("successRegister", "user", accountDto);
        }

    }

    private Company createUserAccount(CompanyDto accountDto, BindingResult result) {
        Company registered = null;
        try {
            registered = service.registerNewCompany(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }
}
