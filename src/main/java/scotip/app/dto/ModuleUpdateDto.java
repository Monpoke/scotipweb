package scotip.app.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import scotip.app.validation.PasswordMatches;
import scotip.app.validation.ValidEmail;
import scotip.app.validation.ValidLibraryFiles;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Pierre on 26/04/2016.
 */

public class ModuleUpdateDto {

    @NotBlank(message = "Company name can't be empty.")
    private String model;

    @NotNull
    @ValidLibraryFiles
    private String libraryFile;

    @NotNull(message = "Please provide a description")
    @Length(max = 40, message = "The description could contains 40 maximum characters.")
    private String description;

    /**
     * @todo SECURITY BREACH: have to check 0 and -1
     */
    @Min(value=-1, message="Phone key cannot be under 1")
    @Max(value=9, message="Phone key cannot be greater than 9")
    private int phoneKey;

    @NotNull(message="Skip file can't be null")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoneKey() {
        return phoneKey;
    }

    public void setPhoneKey(int phoneKey) {
        this.phoneKey = phoneKey;
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
