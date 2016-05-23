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

package scotip.app.validation.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import scotip.app.dto.ModuleDto;
import scotip.app.model.Module;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pierre on 21/05/2016.
 */
public class ModuleAddValidator implements Validator {
    private Module module;

    public ModuleAddValidator(Module module) {

        this.module = module;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ModuleDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ModuleDto moduleDto = (ModuleDto) o;


    }

}
