package scotip.app.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import scotip.app.model.Company;

import javax.validation.constraints.NotNull;

/**
 * Created by svevia on 18/05/2016.
 */
public class OperatorDto {


    @NotBlank(message = "The opertaor login cannot be blank")
    private String name;

    @NotNull(message = "A password should be setted.")
    @Length(min = 4, max = 25, message = "The password should contains between 4 and 25 characters.")
    private String password;
    private String matchingPassword;


    // NO COMMENT, BECAUSE SET BY THE SOFTWARE
    private Company company;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
