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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import scotip.app.dto.ModuleUpdateDto;
import scotip.app.dto.MohGroupAdd;
import scotip.app.exceptions.ModuleModelNotFoundException;
import scotip.app.exceptions.ModuleNotFoundException;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.model.Module;
import scotip.app.model.Switchboard;
import scotip.app.service.module.ModuleService;
import scotip.app.service.moduleModel.ModuleModelService;
import scotip.app.service.sounds.SoundsService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
@SessionAttributes("mohController")
public class MohController extends SwitchboardAppController {

    @Autowired
    ModuleModelService moduleModelService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    SwitchboardService switchboardService;

    @Autowired
    SoundsService soundsService;


    @RequestMapping("/u/switchboard/{sid}/moh")
    public String mohGroupsHome(@PathVariable int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);
        modelMap.addAttribute("mohGroups", switchboard.getMohGroups());

        return "pages/moh/list";
    }

    @RequestMapping(value = "/u/switchboard/{sid}/moh/add", method = RequestMethod.GET)
    public String mohGroupsAdd(@PathVariable int sid) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        return "pages/moh/add";
    }

    @RequestMapping(value = "/u/switchboard/{sid}/moh/add", method = RequestMethod.POST)
    public String mohGroupsAddPOST(@PathVariable int sid, @Valid MohGroupAdd mohGroupAdd, BindingResult bindingResult, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        if (bindingResult.hasErrors()) {
            modelMap.put("errors", bindingResult.getAllErrors());
            modelMap.put("dto", mohGroupAdd);

            return "pages/moh/add";
        } else {
            soundsService.saveMOHGroup(mohGroupAdd, switchboard);
            return "redirect:/u/switchboard/" + sid + "/moh";
        }

    }


    @RequestMapping("/u/switchboard/{sid}/moh/{mid}")
    public String mohGroupsAdd(@PathVariable int sid, @PathVariable int mid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }


        return "pages/moh/group";
    }

    @RequestMapping("/u/switchboard/{sid}/moh/{mid}/delete")
    public String mohGroupsDelete(@PathVariable int sid, @PathVariable int mid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        soundsService.removeMOHGroup(switchboard, mid);
        return "redirect:/u/switchboard/" + sid + "/moh?del=1";
    }


}
