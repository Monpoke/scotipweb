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
