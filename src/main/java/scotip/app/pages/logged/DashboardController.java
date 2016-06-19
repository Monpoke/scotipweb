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

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import scotip.app.dao.switchboard.CallDao;
import scotip.app.dto.CompanyDto;
import scotip.app.infos.IncomingCallsLogsWeek;
import scotip.app.model.Company;
import scotip.app.pages.AppLogged;
import scotip.app.service.company.CompanyService;
import scotip.app.service.operator.OperatorService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
public class DashboardController extends AppLogged {

    @Autowired
    private CompanyService company;

    @Autowired
    private SwitchboardService switchboard;

    @Autowired
    private OperatorService operator;

    @Autowired
    private CallDao callDao;

    @RequestMapping("/u/edit")
    public String operators(ModelMap model) {

        CompanyDto companyDto = new CompanyDto();

        Company c = getCurrentCompany();
        companyDto.setName(c.getName());
        companyDto.setAddress(c.getAddress());
        companyDto.setAddress2(c.getAddress2());
        companyDto.setCity(c.getCity());
        companyDto.setContactMail(c.getContactMail());
        companyDto.setContactName(c.getContactName());
        companyDto.setContactPhone(c.getContactPhone());
        companyDto.setCountry(c.getCountry());
        companyDto.setPhoneNumber(c.getPhoneNumber());
        companyDto.setPostcode(c.getPostcode());
        companyDto.setPassword(c.getPassword());

        model.addAttribute("company", companyDto);

        return "pages/edit";
    }

    @RequestMapping({"/u", "/u/dashboard"})
    protected String dashboard(ModelMap model) {
        int nbrSwitchboard = switchboard.getAllSwitchboardFromCompany(getCurrentCompany()).size();
        model.addAttribute("nbrSwitchboard", nbrSwitchboard);

        int nbrOperator = operator.getAllOperators(getCurrentCompany()).size();
        model.addAttribute("nbrOperator", nbrOperator);

        List<IncomingCallsLogsWeek> incomingCallsByMonth = callDao.getIncomingCallsByWeek(getCurrentCompany());
        model.addAttribute("callLogs", incomingCallsByMonth);


        // GET CALLS LOGS
        System.out.println("Size: " + incomingCallsByMonth.size());

        // labels
        List<String> labels = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();

        int dayDisplay = 7;

        calendar.add(Calendar.DAY_OF_MONTH, -dayDisplay);
        for (int i = 0; i < dayDisplay; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            labels.add(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1));
        }
        model.addAttribute("labels", new Gson().toJson(labels));

        // values, foreach on each day
        List<Long> values = new ArrayList<>();

        calendar.add(Calendar.DAY_OF_MONTH, -dayDisplay);
        for (int i = 0; i < dayDisplay; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            int currentDay = calendar.get(Calendar.DAY_OF_MONTH),
                    currentMonth = calendar.get(Calendar.MONTH);

            boolean dataFound = false;

            // foreach for the day
            for (IncomingCallsLogsWeek incoming : incomingCallsByMonth) {

                if (incoming.getDay() == currentDay && (incoming.getMonth()-1) == currentMonth) {
                    dataFound=true;

                    // ADD DATA TO CHART
                    values.add(incoming.getTotal());
                    break;
                }
            }

            // add data
            if(!dataFound){
                values.add(0l);
            }

        }
        model.addAttribute("plots",new Gson().toJson(values));


        return ("pages/static/index");
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView registerCompanyAccount(
            @ModelAttribute("company") @Valid CompanyDto companyDto,
            BindingResult result, WebRequest request, Errors errors) {

        if (!result.hasErrors()) {
            company.update(getCurrentCompany(), companyDto);
            company.refresh(getCurrentCompany());
        }

        // REST HERE
        if (result.hasErrors()) {
            System.out.println("ERRORS !");
            List<ObjectError> allErrors = result.getAllErrors();
            Iterator<ObjectError> iterator = allErrors.iterator();

            while (iterator.hasNext()) {
                System.out.println(iterator.next().getDefaultMessage());
            }

            return new ModelAndView("pages/edit", "company", companyDto);
        } else {
            return new ModelAndView("pages/edit", "company", companyDto);
        }

    }


}
