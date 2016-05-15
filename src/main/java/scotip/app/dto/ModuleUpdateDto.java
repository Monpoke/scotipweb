package scotip.app.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import scotip.app.validation.PasswordMatches;
import scotip.app.validation.ValidEmail;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Pierre on 26/04/2016.
 */

public class ModuleUpdateDto {

    @NotBlank(message = "Company name can't be empty.")
    private String model;

    @NotNull
    private String libraryFile;


    private boolean canSkipFile;

    @Min(value=1, message = "Module id should be provided.")
    private int moduleId;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLibraryFile() {
        return libraryFile;
    }

    public void setLibraryFile(String libraryFile) {
        this.libraryFile = libraryFile;
    }

    public boolean isCanSkipFile() {
        return canSkipFile;
    }

    public void setCanSkipFile(boolean canSkipFile) {
        this.canSkipFile = canSkipFile;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }


    @Override
    public String toString() {
        return "ModuleUpdateDto{" +
                "model='" + model + '\'' +
                ", libraryFile='" + libraryFile + '\'' +
                ", canSkipFile='" + canSkipFile + '\'' +
                ", moduleId=" + moduleId +
                '}';
    }
}
