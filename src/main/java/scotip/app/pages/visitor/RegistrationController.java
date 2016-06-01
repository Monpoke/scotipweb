/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package scotip.app.pages.visitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import scotip.app.dto.CompanyDto;
import scotip.app.exceptions.EmailExistsException;
import scotip.app.model.Company;
import scotip.app.service.company.CompanyService;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

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
            result.rejectValue("ContactMail", "message.regError");
        }


        // REST HERE
        if (result.hasErrors()) {
            System.out.println("ERRORS ! :D");
            List<ObjectError> allErrors = result.getAllErrors();
            Iterator<ObjectError> iterator = allErrors.iterator();

            while(iterator.hasNext()){
                System.out.println(iterator.next().getDefaultMessage());
            }

            return new ModelAndView("pages/nonLogged/registration", "company", accountDto);
        } else {
            return new ModelAndView("pages/nonLogged/registrationOk", "company", accountDto);
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
