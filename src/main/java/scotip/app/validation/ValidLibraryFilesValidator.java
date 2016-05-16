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
import java.util.List;

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
     * @param obj
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (!(obj instanceof String)) {
            return false;
        }

        String files = (String) obj;
        if (files.isEmpty()) {
            return true;
        }

        String[] allFiles = files.split("&");

        List<SoundLibrary> soundsFromList = soundsService.getSoundsFromList(allFiles);
        return soundsFromList.size() == allFiles.length;
    }
}