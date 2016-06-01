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
import org.springframework.web.bind.annotation.*;
import scotip.app.dto.QueueDto;
import scotip.app.dto.QueueOperatorDto;
import scotip.app.exceptions.NotFoundException;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.model.Operator;
import scotip.app.model.Queue;
import scotip.app.model.Switchboard;
import scotip.app.service.module.ModuleService;
import scotip.app.service.modulemodel.ModuleModelService;
import scotip.app.service.operator.OperatorService;
import scotip.app.service.operator.QueueService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
@SessionAttributes("queues")
public class QueuesController extends SwitchboardAppController {

    @Autowired
    ModuleModelService moduleModelService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    SwitchboardService switchboardService;

    @Autowired
    QueueService queueService;

    @Autowired
    OperatorService operatorService;


    @RequestMapping("/u/switchboard/{sid}/queues")
    public String queuesList(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);
        modelMap.addAttribute("queues", queueService.getQueuesFromSwitchboard(switchboard));


        return "pages/switchboard/queues/list";
    }


    @RequestMapping(value = "/u/switchboard/{sid}/queues/add", method = RequestMethod.POST)
    @ResponseBody
    public String queueAdd(@PathVariable("sid") int sid, @Valid QueueDto queueDto, BindingResult bindingResult) throws SwitchboardNotFoundException, IOException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        if (!bindingResult.hasErrors()) {
            queueService.registerNewQueue(switchboard, queueDto);
            return "ok";
        } else {
            return new Gson().toJson(bindingResult.getAllErrors());
        }
    }


    @RequestMapping(value = "/u/switchboard/{sid}/queues/{qid}/remove")
    public String queueRemove(@PathVariable("sid") int sid, @PathVariable("qid") int qid) throws NotFoundException, IOException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        Queue queue = queueService.getQueueWithSwitchboardAndId(switchboard, qid);
        if (queue == null) {
            throw new NotFoundException("Queue not found.");
        }

        queueService.removeQueue(queue);
        return "redirect:/u/switchboard/" + sid + "/queues";
    }


    @RequestMapping(value = "/u/switchboard/{sid}/queues/{qid}/operators", method = RequestMethod.GET)
    public String queueOperators(@PathVariable("sid") int sid, @PathVariable("qid") int qid, ModelMap modelMap) throws NotFoundException, IOException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        Queue queue = queueService.getQueueWithSwitchboardAndId(switchboard, qid);
        if (queue == null) {
            throw new NotFoundException("Queue not found.");
        }

        modelMap.put("switchboard",switchboard);
        modelMap.put("queue",queue);

        // get all operators
        List<Operator> operators = operatorService.getAllOperators(getCurrentCompany());
        modelMap.put("operators", operators);

        return "pages/switchboard/queues/operators";
    }

    @RequestMapping(value = "/u/switchboard/{sid}/queues/{qid}/operators", method = RequestMethod.POST)
    public String queueEditOperators(@Valid QueueOperatorDto queueOperatorDto, BindingResult bindingResult,
                                     @PathVariable("sid")int sid, @PathVariable("qid") int qid, ModelMap modelMap) throws NotFoundException, IOException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        Queue queue = queueService.getQueueWithSwitchboardAndId(switchboard, qid);
        if (queue == null) {
            throw new NotFoundException("Queue not found.");
        }

        modelMap.put("switchboard",switchboard);
        modelMap.put("queue",queue);

        // get all operators
        List<Operator> operators = operatorService.getAllOperators(getCurrentCompany());
        modelMap.put("operators", operators);


        if(bindingResult.hasErrors()){
            modelMap.put("errors", bindingResult.getAllErrors());
        }
        else {
            modelMap.put("success", "true");
            queueService.saveOperators(queue, queueOperatorDto);
        }


        return "pages/switchboard/queues/operators";
    }


}
