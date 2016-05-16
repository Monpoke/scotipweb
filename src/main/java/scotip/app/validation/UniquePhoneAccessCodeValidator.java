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
import scotip.app.dto.SwitchboardDto;
import scotip.app.service.line.LineService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Pierre on 29/04/2016.
 */
public class UniquePhoneAccessCodeValidator implements ConstraintValidator<UniquePhoneAccessCode, Object> {

    @Autowired
    LineService lineService;


    @Override
    public void initialize(UniquePhoneAccessCode uniquePhoneAccessCode) {

    }

    /**
     * Check if code is available.
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SwitchboardDto switchboardDto = (SwitchboardDto) o;

        if(switchboardDto.getSharedLine() == null || switchboardDto.getPhoneCodeAccess() == null){
            return false;
        }

        return lineService.isAccessCodeAvailable(switchboardDto.getPhoneCodeAccess(),switchboardDto.getSharedLine());
    }
}
