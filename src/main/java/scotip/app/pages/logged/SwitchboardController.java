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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.model.*;
import scotip.app.service.module.ModuleService;
import scotip.app.service.modulemodel.ModuleModelService;
import scotip.app.service.operator.OperatorService;
import scotip.app.service.sounds.SoundsService;
import scotip.app.service.switchboard.SwitchboardService;

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

    @Autowired
    SoundsService soundsService;

    @Autowired
    OperatorService operatorService;


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

        // get all operators attributed to this switchboard
        operatorService.getOperatorsFromSwitchboard(switchboard);


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

    @RequestMapping("/u/switchboard/{sid}/config")
    public String configurationEdit(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);

        return "pages/switchboard/config";
    }



}
