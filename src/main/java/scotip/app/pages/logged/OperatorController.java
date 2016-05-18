package scotip.app.pages.logged;

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
import scotip.app.dto.CompanyDto;
import scotip.app.dto.OperatorDto;
import scotip.app.model.Operator;
import scotip.app.service.company.CompanyService;
import scotip.app.service.operator.OperatorService;

import javax.validation.Valid;

/**
 * Created by svevia on 18/05/2016.
 */

@Controller

public class OperatorController {

    @Autowired
    private OperatorService operator;

    @Autowired
    private CompanyService company;

    @RequestMapping("/u/operator/{id}")
    public String operators(@PathVariable("id") int id, ModelMap model) {

        model.put("list", operator.getAllOperator(id));
        OperatorDto operatorDto = new OperatorDto();
        model.addAttribute("operator", operatorDto);

        return "pages/static/operator";
    }


    @RequestMapping(value = "/u/newOperator/{id}", method = RequestMethod.POST)

    public String newOperators(@PathVariable("id") int id,ModelMap model
            ,@ModelAttribute("operator") @Valid OperatorDto accountDto
            , BindingResult result, WebRequest request, Errors errors) {
        accountDto.setCompany(company.findById(id));
        operator.registerNewOperator(accountDto);



        model.put("list", operator.getAllOperator(id));
        OperatorDto operatorDto = new OperatorDto();
        model.addAttribute("operator", operatorDto);

        return "pages/static/operator";
    }


}
