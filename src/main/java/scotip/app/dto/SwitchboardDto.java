package scotip.app.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import scotip.app.model.Line;
import scotip.app.model.Switchboard;
import scotip.app.validation.PasswordMatches;
import scotip.app.validation.UniquePhoneAccessCode;
import scotip.app.validation.ValidEmail;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Pierre on 26/04/2016.
 */

@UniquePhoneAccessCode
public class SwitchboardDto {

    @NotBlank(message = "The service name cannot be blank")
    private String name;

    @NotBlank(message = "The service description should be provided.")
    private String description;


    @NotNull(message = "The service code should be an integer.")
    @Range(min = 50, max = 9999, message = "The code service should be between 50 and 9999.")
    private Integer phoneCodeAccess;

    @NotNull(message = "The shared line is invalid.")
    private Line sharedLine;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPhoneCodeAccess() {
        return phoneCodeAccess;
    }

    public void setPhoneCodeAccess(Integer phoneCodeAccess) {
        this.phoneCodeAccess = phoneCodeAccess;
    }

    public Line getSharedLine() {
        return sharedLine;
    }

    public void setSharedLine(Line sharedLine) {
        this.sharedLine = sharedLine;
    }
}
