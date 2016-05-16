package scotip.app.pages.logged;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import scotip.app.Application;
import scotip.app.dto.SwitchboardDto;
import scotip.app.exceptions.ModuleNotFoundException;
import scotip.app.exceptions.NoRootModuleException;
import scotip.app.exceptions.SeveralRootModuleException;
import scotip.app.exceptions.SwitchboardNotFoundException;
import scotip.app.infos.AlertMessage;
import scotip.app.model.*;
import scotip.app.pages.App;
import scotip.app.service.company.CompanyService;
import scotip.app.service.line.LineService;
import scotip.app.service.module.ModuleService;
import scotip.app.service.sounds.SoundsService;
import scotip.app.service.switchboard.SwitchboardService;
import scotip.app.tools.UploadedFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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

    private UploadedFile uploadedFile;


    DialplanController() {
        uploadedFile = new UploadedFile();
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


        // HAVE TO FIND LIBRARY SOUNDS
        getLibrarySounds(modelMap);

        return ("pages/switchboard/dialplan");
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


    @RequestMapping(value = "/u/module/upload/{module}", method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartHttpServletRequest request, HttpServletResponse response, @PathVariable("module") int moduleId) throws ModuleNotFoundException {

        // check module is correct
        Module module = moduleService.findByIdAndCompany(moduleId, getCurrentCompany());
        if (module == null) {
            throw new ModuleNotFoundException();
        }

        //0. notice, we have used MultipartHttpServletRequest

        //1. get the files from the request object
        Iterator<String> itr = request.getFileNames();

        MultipartFile mpf = request.getFile(itr.next());


        String name = moduleId + ".mp3",
                path = Application.UPLOAD_DIR + "/" + name;

        if (!mpf.isEmpty()) {
            try {
                File file = new File(path);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(file));
                FileCopyUtils.copy(mpf.getInputStream(), stream);
                stream.close();
                System.out.println(mpf.getOriginalFilename() + " uploaded!");



                // have to convert and store
                convertFile(file.getAbsolutePath(), name, mpf, module);


                return "ok";
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        } else {
            System.out.println("empty file");
            return "empty";
        }


    }

    private void convertFile(String path, String name, MultipartFile mpf, Module module) {
        try {
            // VARIABLES
            String outputPath = "/usr/scotip/usersounds/"+module.getSwitchboard().getSid()+"/"+name;

            // ECHO
            System.out.println("Convert from " + path + " to " +outputPath);

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
            module.getSettings().put("file", "custom/"+module.getSwitchboard().getSid()+"/"+module.getMid());
            moduleService.save(module);

            // RELOAD
            switchboardService.notifyServerDialplanReload(module.getSwitchboard());


        } catch (IOException io) {
            io.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        try {

            response.setContentType(uploadedFile.type);
            response.setContentLength(uploadedFile.length);
            FileCopyUtils.copy(uploadedFile.bytes, response.getOutputStream());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
