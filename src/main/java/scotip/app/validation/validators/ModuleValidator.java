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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pierre on 21/05/2016.
 */
public class ModuleValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ModuleDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ModuleDto moduleDto = (ModuleDto) o;

        if(moduleDto.getModuleType() == null){
            errors.rejectValue("modelType","modelType","The model type is invalid.");
            return;
        }

        switch (moduleDto.getModuleType().getSlug()) {
            case "userinput":
                checkUserinput(moduleDto, errors);
                break;

            case "operator":
                checkOperator(moduleDto, errors);
                break;
            case "queue":
                checkQueue(moduleDto, errors);
                break;


        }

    }

    /**
     * To check userInput module!
     *
     * @param moduleDto
     * @param errors
     */
    private void checkUserinput(ModuleDto moduleDto, Errors errors) {
        if (moduleDto.getVariable().isEmpty()) {
            errors.rejectValue("variable", "variable", "A variable name should be provided!");
        }

        int numberFormatMin = -1;
        int numberFormatMax = -2;

        try {
            numberFormatMin = Integer.parseInt(moduleDto.getNumberFormatMin());
            numberFormatMax = Integer.parseInt(moduleDto.getNumberFormatMax());



            if (numberFormatMin < 1) {
                errors.rejectValue("numberFormatMin", "numberFormatMin", "Minimum size of input should be greater than 0.");
            }
            if (numberFormatMax > 25) {
                errors.rejectValue("numberFormatMax", "numberFormatMax", "Maximum size of input should be smaller or equals to 25.");
            }

            if (numberFormatMax < numberFormatMin) {
                errors.rejectValue("numberFormatMax", "numberFormatMax", "Maximum size of input should be greater or equal to min.");
            }


        } catch (NumberFormatException e) {
            errors.rejectValue("numberFormatMin", "numberFormatMin", "Please enter a valid number for input format.");

        }


        // CHECK URL
        if (!moduleDto.getUrlCheck().isEmpty()) {
            // have to check a good url
            checkRightURL(moduleDto.getUrlCheck(), moduleDto, errors);
        }


    }


    private void checkRightURL(String urlCheck, ModuleDto moduleDto, Errors errors) {
        Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");

        Matcher m = p.matcher(urlCheck);
        if (!m.matches()) {
            errors.rejectValue("urlCheck", "urlCheck", "The URL is invalid!");
        }
    }

    /**
     * To check operator module!
     *
     * @param moduleDto
     * @param errors
     */
    private void checkOperator(ModuleDto moduleDto, Errors errors) {
        if (moduleDto.getOperator() == null) {
            errors.rejectValue("operator", "operator", "The operator is invalid!");
        } else if (moduleDto.getOperator().getCompany().getId() != moduleDto.getCompany().getId()) {
            errors.rejectValue("operator", "operator", "The operator is not associated to your company!");
        }

        checkMoh(moduleDto, errors);
    }

    private void checkMoh(ModuleDto moduleDto, Errors errors) {
        if (moduleDto.getMoh() != null) {
            if (moduleDto.getMoh().getSwitchboard().getSid() != moduleDto.getSwitchboard().getSid()) {
                errors.rejectValue("moh", "moh", "This MOH group is not associated to this switchboard!");
            }
        }
    }

    /**
     * To check queue module!
     *
     * @param moduleDto
     * @param errors
     */
    private void checkQueue(ModuleDto moduleDto, Errors errors) {
        if (moduleDto.getQueue() == null) {
            errors.rejectValue("queue", "queue", "The queue is invalid!");
        } else if (moduleDto.getQueue().getSwitchboard().getSid() != moduleDto.getSwitchboard().getSid()) {
            errors.rejectValue("queue", "queue", "This queue is not associated to this switchboard!");
        }

        checkMoh(moduleDto, errors);
    }


}
