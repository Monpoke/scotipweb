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

package scotip.app.validation;

import org.springframework.beans.factory.annotation.Autowired;
import scotip.app.dto.CompanyDto;
import scotip.app.model.SoundLibrary;
import scotip.app.service.module.ModuleService;
import scotip.app.service.sounds.SoundsService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Pierre on 26/04/2016.
 */
public class ValidLibraryFilesValidator implements ConstraintValidator<ValidLibraryFiles, Object> {

    @Autowired
    SoundsService soundsService;


    @Override
    public void initialize(ValidLibraryFiles constraintAnnotation) {

    }

    /**
     * Checks if files exists in database as library.
     *
     * @param obj
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (!(obj instanceof Map)) {
            return false;
        }

        Map<String, String> files = (Map<String, String>) obj;

        if (files.isEmpty()) {
            return true;
        }

        for (Iterator<Map.Entry<String, String>> it = files.entrySet().iterator();
             it.hasNext(); ) {
            Map.Entry<String, String> entry = it.next();

            // FILENAME
            String fileName = entry.getKey();
            if(!fileName.matches("(inputError|message|unavailable)")){ return false; }

            // VALUE
            String value = entry.getValue();

            // SKIP
            if(value.isEmpty()){
                continue;
            }

            // SPLIT ON CONTINUOUS CHAIN
            String[] chains = value.split("&");

            for (int i = 0, t = chains.length; i < t; i++) {
                // PREVENT LAST &
                if(chains[i].trim().isEmpty()){
                    return false;
                }
                System.out.println("chain"+i+" =>" + chains[i]);

                // IF CUSTOM
                String[] split = chains[i].split("/");
                if (split.length != 2 || !split[0].matches("(library|custom)")) {
                    System.out.println("!=2 or != library/custom : " + split[0] + "" + split.length);
                    return false;
                }

                if (split[0].equals("custom")) {
                    if (!split[1].matches("(inputError|message|unavailable)")) {
                        System.out.println("Custom invalid");
                        return false;
                    }
                } else {
                    // LIBRARY
                    if (split[1].trim().isEmpty() || soundsService.getSoundSlug("library/" + split[1]) == null) {
                        System.out.println("library file invalid -> " + split[1]);
                        return false;
                    }
                }

            }

        }

        return true;
    }
}