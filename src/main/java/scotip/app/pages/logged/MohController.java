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
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scotip.app.Application;
import scotip.app.dto.MohGroupAdd;
import scotip.app.dto.MohUploadDto;
import scotip.app.exceptions.*;
import scotip.app.model.MohFile;
import scotip.app.model.MohGroup;
import scotip.app.model.Switchboard;
import scotip.app.service.module.ModuleService;
import scotip.app.service.modulemodel.ModuleModelService;
import scotip.app.service.sounds.SoundsService;
import scotip.app.service.switchboard.SwitchboardService;

import javax.validation.Valid;
import java.io.*;
import java.util.*;

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
    public String mohGroupsAdd(@PathVariable int sid, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }
        modelMap.put("switchboard", switchboard);

        return "pages/moh/add";
    }

    @RequestMapping(value = "/u/switchboard/{sid}/moh/add", method = RequestMethod.POST)
    public String mohGroupsAddPOST(@PathVariable int sid, @Valid MohGroupAdd mohGroupAdd, BindingResult bindingResult, ModelMap modelMap) throws SwitchboardNotFoundException {
        Switchboard switchboard = getSwitchboard(sid);
        if (switchboard == null) {
            throw new SwitchboardNotFoundException();
        }

        modelMap.put("switchboard", switchboard);


        if (bindingResult.hasErrors()) {
            modelMap.put("errors", bindingResult.getAllErrors());
            modelMap.put("dto", mohGroupAdd);

            return "pages/moh/add";
        } else {
            soundsService.saveMOHGroup(mohGroupAdd, switchboard);
            return "redirect:/u/switchboard/" + sid + "/moh";
        }

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


    @RequestMapping("/u/switchboard/{sid}/moh/{mid}")
    public String mohGroupsList(@PathVariable int sid, @PathVariable int mid, ModelMap modelMap) throws SwitchboardNotFoundException, MOHNotFoundException {
        MohGroup mohGroup = soundsService.getMohGroupWithIdAndSwitchboardAndCompany(mid, sid, getCurrentCompany().getId());
        if (mohGroup == null) {
            throw new MOHNotFoundException();
        }

        modelMap.put("switchboard", mohGroup.getSwitchboard());
        modelMap.put("group", mohGroup);
        modelMap.put("mohFiles", mohGroup.getFiles());


        return "pages/moh/group";
    }


    /*
    ==============
    === UPLOAD ===
    ==============
     */
    @RequestMapping(value = "/u/switchboard/{sid}/moh/{mid}/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@PathVariable int sid, @PathVariable int mid, @Valid MohUploadDto mohUploadDto, BindingResult bindingResult) throws ModuleNotFoundException, NotFoundException, OperationException {
        MohGroup mohGroup = soundsService.getMohGroupWithIdAndSwitchboardAndCompany(mid, sid, getCurrentCompany().getId());
        if (mohGroup == null) {
            throw new MOHNotFoundException();
        }

        //0. notice, we have used MultipartHttpServletRequest
        if (mohUploadDto.getFile() == null) {
            bindingResult.rejectValue("file", "file", "You have to provide a file.");
        }

        // IF ERROR
        if (bindingResult.hasErrors()) {
            return new Gson().toJson(bindingResult.getAllErrors());
        }


        if (!mohUploadDto.getFile().isEmpty()) {
            try {
                // SAVE TO DB, TO GET A DATABASE
                MohFile mohFile = new MohFile();
                mohFile.setGroup(mohGroup);
                mohFile.setName(mohUploadDto.getName());
                mohFile.setPath("");
                int mohFileID = soundsService.saveMohFILE(mohFile);



                String name = sid + "_" + mohFileID + ".mp3",
                        path = Application.UPLOAD_DIR + "/" + name;


                File file = new File(path);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(file));
                FileCopyUtils.copy(mohUploadDto.getFile().getInputStream(), stream);
                stream.close();
                System.out.println(mohUploadDto.getFile().getOriginalFilename() + " uploaded under this name: " + path + "!");


                // have to convert and store
                convertFile(file.getAbsolutePath(), name, mohUploadDto.getFile(), mohFile);


                Map<String, String> map = new HashMap<>();
                map.put("status", "ok");
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


    private void convertFile(String tmpPath, String name, MultipartFile file, MohFile mohFile) {
        try {
            // VARIABLES
            String outputPath = "/usr/scotip/usermoh/files/" + mohFile.getGroup().getGroupId() + "/" + name;

            // ECHO
            System.out.println("Convert from " + tmpPath + " to " + outputPath);

            // RUNTIME
            Runtime r = Runtime.getRuntime();
            Process p = r.exec("uploadMP3MOH " + mohFile.getGroup().getGroupId() + " " + tmpPath);
            p.waitFor();
            BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";

            while ((line = b.readLine()) != null) {
                System.out.println(line);
            }

            b.close();

            System.out.println("Notify server reload");
            // RELOAD
            soundsService.notifyServerReload(getCurrentCompany());


        } catch (IOException io) {
            io.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
