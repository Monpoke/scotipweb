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