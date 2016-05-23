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
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import scotip.app.Application;
import scotip.app.dto.ModuleDto;
import scotip.app.exceptions.*;
import scotip.app.infos.AlertMessage;
import scotip.app.model.*;
import scotip.app.model.Queue;
import scotip.app.service.company.CompanyService;
import scotip.app.service.line.LineService;
import scotip.app.service.module.ModuleService;
import scotip.app.service.operator.OperatorService;
import scotip.app.service.sounds.SoundsService;
import scotip.app.service.switchboard.SwitchboardService;
import scotip.app.tools.UploadedFile;
import scotip.app.validation.validators.ModuleValidator;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
@SessionAttributes("dialplan")
public class DialplanController extends SwitchboardAppController {

    /**
     * SERVICES
     */
    @Autowired
    private CompanyService companyService;

    @Autowired
    private LineService lineService;

    @Autowired
    private SwitchboardService switchboardService;

    @Autowired
    private SoundsService soundsService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private OperatorService operatorService;

    DialplanController() {

    }

    /**
     * Page to choose a board.
     *
     * @param modelMap
     * @return
     */
    @RequestMapping({"/u/switchboard/{sid}/dialplan"})
    protected String dialplanConfig(@PathVariable("sid") int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.addAttribute("switchboard", switchboard);


        // Get Modules
        modelMap.addAttribute("modules", switchboard.getModules());


        Module rootModule = null;
        try {
            rootModule = getRootModule(switchboard.getModules());
        } catch (NoRootModuleException e) {
            e.printStackTrace();
            modelMap.addAttribute("pageSpecialAlert", new AlertMessage("danger", e.getMessage()));
        } catch (SeveralRootModuleException e) {
            e.printStackTrace();
            modelMap.addAttribute("pageSpecialAlert", new AlertMessage("danger", e.getMessage()));
        }
        modelMap.addAttribute("rootModule", rootModule);


        // JSON data
        Gson gson = new Gson();

        modelMap.addAttribute("queuesList", gson.toJson(getQueuesJSON(switchboard)));
        modelMap.addAttribute("mohList", gson.toJson(getMohGroupsJSON(switchboard)));
        modelMap.addAttribute("operatorsList", gson.toJson(getOperatorsJSON()));


        // HAVE TO FIND LIBRARY SOUNDS
        getLibrarySounds(modelMap);


        return ("pages/switchboard/dialplan");
    }

    private List<HashMap<String, Object>> getQueuesJSON(Switchboard switchboard) {
        List<Queue> queues = switchboard.getQueues();
        List<HashMap<String, Object>> out = new ArrayList<>();

        Iterator<Queue> iterator = queues.iterator();
        while (iterator.hasNext()) {
            Queue next = iterator.next();
            out.add(next.getPublicData());
        }
        return out;
    }


    private List<HashMap<String, Object>> getOperatorsJSON() {
        List<Operator> operators = operatorService.getAllOperators(getCurrentCompany());
        List<HashMap<String, Object>> out = new ArrayList<>();

        System.out.println(operators.size());

        Iterator<Operator> iterator = operators.iterator();
        while (iterator.hasNext()) {
            Operator next = iterator.next();
            System.out.println(next);
            out.add(next.getPublicData());
        }
        return out;
    }


    /***
     * Get MOH groups
     *
     * @param switchboard
     * @return
     */
    private List<HashMap<String, Object>> getMohGroupsJSON(Switchboard switchboard) {
        List<MohGroup> mohGroups = switchboard.getMohGroups();
        List<HashMap<String, Object>> out = new ArrayList<>();

        Iterator<MohGroup> iterator = mohGroups.iterator();
        while (iterator.hasNext()) {
            MohGroup next = iterator.next();
            out.add(next.getPublicData());
        }
        return out;
    }


    @RequestMapping(path = "/u/module/update/{module}", method = RequestMethod.POST)
    @ResponseBody
    public String updateModule(@PathVariable("module") int moduleId, @Valid ModuleDto moduleDto, BindingResult bindingResult) throws ModuleNotFoundException {

        moduleDto.setModuleId(moduleId);

        Module module = moduleService.findByIdAndCompany(moduleDto.getModuleId(), getCurrentCompany());
        moduleDto.setCompany(getCurrentCompany());
        moduleDto.setSwitchboard(module.getSwitchboard());

        if (module == null) {
            throw new ModuleNotFoundException();
        }


        // CREATE A CUSTOM VALIDATOR
        ModuleValidator moduleValidator = new ModuleValidator(module);
        moduleValidator.validate(moduleDto, bindingResult);

        /**
         * There is no error, so we can save
         */
        if (!bindingResult.hasErrors()) {


            // save the file
            moduleService.saveUpdate(module, moduleDto);
            System.out.println(moduleDto.toString());
            return "ok";

        } else {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return new Gson().toJson(allErrors);
        }
    }


    /**
     * Gets the library sounds
     *
     * @param modelMap
     */
    private void getLibrarySounds(ModelMap modelMap) {
        List<SoundLibrary> librarySounds = soundsService.getLibrarySounds();
        modelMap.put("librarySounds", librarySounds);
    }

    /**
     * Find the root module
     *
     * @param modules
     * @return
     */
    private Module getRootModule(List<Module> modules) throws NoRootModuleException, SeveralRootModuleException {
        Module root = null;

        // module level = 1, parent=null
        for (Iterator<Module> i = modules.iterator(); i.hasNext(); ) {
            Module next = i.next();

            if (next.getModuleParent() == null && next.getModuleLevel() == 1) {
                if (root != null) {
                    throw new SeveralRootModuleException();
                }

                root = next;
            }
        }

        if (root == null) {
            throw new NoRootModuleException();
        }

        return root;
    }


    @RequestMapping(value = "/u/module/upload/{codeModule}", method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartHttpServletRequest request, HttpServletResponse response, @PathVariable("codeModule") int codeModule) throws ModuleNotFoundException, NotFoundException {
        String codeMod = "" + codeModule;
        if (codeMod.length() < 3) {
            throw new NotFoundException("Invalid code module.");
        }

        int nb1 = Integer.parseInt("" + codeMod.charAt(0));
        int moduleId = Integer.parseInt(codeMod.substring((1 + nb1)));
        String msgCode = codeMod.substring(1, (1 + nb1));


        // CHECK MESSAGE CODE
        switch (msgCode) {
            case "1":
                msgCode = "message";
                break;
            case "2":
                msgCode = "inputError";
                break;
            case "3":
                msgCode = "unavailable";
                break;
            default:
                throw new NotFoundException("Invalid code module... " + msgCode);
        }


        // check module is correct
        Module module = moduleService.findByIdAndCompany(moduleId, getCurrentCompany());
        if (module == null) {
            throw new ModuleNotFoundException();
        }

        //0. notice, we have used MultipartHttpServletRequest

        //1. get the files from the request object
        Iterator<String> itr = request.getFileNames();

        MultipartFile mpf = request.getFile(itr.next());

        String name = moduleId + "_" + msgCode + ".mp3",
                path = Application.UPLOAD_DIR + "/" + name;

        if (!mpf.isEmpty()) {
            try {
                File file = new File(path);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(file));
                FileCopyUtils.copy(mpf.getInputStream(), stream);
                stream.close();
                System.out.println(mpf.getOriginalFilename() + " uploaded under this name: " + path + "!");


                // have to convert and store
                convertFile(file.getAbsolutePath(), name, mpf, module);

                // SAVE TO DB
                String filename = "custom/" + msgCode;
                module.getFiles().put(msgCode, filename);


                Map<String, String> map = new HashMap<>();
                map.put("status", "ok");
                map.put("filename", filename);
                return new Gson().toJson(map);
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        } else {
            System.out.println("empty file");
            return "empty";
        }


    }

    @ExceptionHandler(value = {FileUploadBase.FileSizeLimitExceededException.class})
    public String uploadError(Exception e) {
        return "error " + e.getMessage();
    }

    private void convertFile(String path, String name, MultipartFile mpf, Module module) {
        try {
            // VARIABLES
            String outputPath = "/usr/scotip/usersounds/" + module.getSwitchboard().getSid() + "/" + name;

            // ECHO
            System.out.println("Convert from " + path + " to " + outputPath);

            // RUNTIME
            Runtime r = Runtime.getRuntime();
            Process p = r.exec("uploadMP3Module " + module.getSwitchboard().getSid() + " " + path);
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();

            // need to register the file
            module.getSettings().put("file", "custom/" + module.getSwitchboard().getSid() + "/" + module.getMid());
            moduleService.save(module);

            // RELOAD
            switchboardService.notifyServerDialplanReload(module.getSwitchboard());


        } catch (IOException io) {
            io.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/u/module/delete/{parent}")
    @ResponseBody
    public String deleteModule(@PathVariable("parent") int parentId, ModelMap modelMap) throws ModuleModelNotFoundException {

        try {
            Module oldModule = moduleService.removeModule(parentId, getCurrentCompany());

            // reload dialplan
            //      switchboardService.notifyServerDialplanReload(oldModule.getSwitchboard());

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("/u/module/create/{parent}/{model}")
    @ResponseBody
    public String createModule(@PathVariable("parent") int parentId, @PathVariable("model") String modelSlug, ModelMap modelMap) throws ModuleModelNotFoundException {

        try {
            Module newModule = moduleService.createNewModule(parentId, modelSlug, getCurrentCompany());

            // reload dialplan
            switchboardService.notifyServerDialplanReload(newModule.getSwitchboard());

            return "ok";
        } catch (OperationException oe) {
            switch (oe.getMessage()) {
                case "childKeyDisabled":
                    return "Can't add a module to this parent: it has a child with phone key disabled!";
                case "parentIsLast":
                    return "Can't add a module to a Queue or Operator module!";
                default:
                    return oe.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
