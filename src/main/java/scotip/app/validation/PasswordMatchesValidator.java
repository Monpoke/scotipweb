package scotip.app.validation;

import scotip.app.dto.CompanyDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Pierre on 26/04/2016.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        CompanyDto company = (CompanyDto) obj;
        return company.getPassword() != null && company.getPassword().equals(company.getMatchingPassword());
    }
}