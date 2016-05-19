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

package scotip.app.pages.logged;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import scotip.app.dto.OperatorDto;
import scotip.app.model.Operator;
import scotip.app.pages.AppLogged;
import scotip.app.service.company.CompanyService;
import scotip.app.service.operator.OperatorService;

import javax.validation.Valid;

/**
 * Created by svevia on 18/05/2016.
 */

@Controller

/**
 * @// TODO: 18/05/2016 Resolve all security breachs...
 */
public class OperatorController extends AppLogged {

    @Autowired
    private OperatorService operator;

    @Autowired
    private CompanyService company;

    @RequestMapping("/u/operator")
    public String operators(ModelMap model) {

        model.put("list", operator.getAllOperator(getCurrentCompany().getId()));
        OperatorDto operatorDto = new OperatorDto();
        model.addAttribute("operator", operatorDto);

        return "pages/static/operator";
    }


    @RequestMapping(value = "/u/newOperator", method = RequestMethod.POST)

    public String newOperators(@ModelAttribute("operator") @Valid OperatorDto accountDto
            , BindingResult result, WebRequest request, Errors errors) {
        accountDto.setCompany(company.findById(getCurrentCompany().getId()));
        operator.registerNewOperator(accountDto);


        return "redirect:/u/operator";

    }

    @RequestMapping("/u/delOperator/{id}")
    public String delOperators(@PathVariable("id") int id) {

        operator.deleteById(id);

        return "redirect:/u/operator/" + getCurrentCompany().getId();

    }

}
