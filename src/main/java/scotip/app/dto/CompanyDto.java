package scotip.app.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import scotip.app.validation.PasswordMatches;
import scotip.app.validation.ValidEmail;

import javax.validation.constraints.NotNull;

/**
 * Created by Pierre on 26/04/2016.
 */

@PasswordMatches
public class CompanyDto {

    @NotBlank(message = "Company name can't be empty.")
    private String name;


    @NotBlank(message = "Address can't be empty.")
    private String address;

    private String address2;

    @NotBlank(message = "Company city can't be empty.")
    private String city;

    @NotBlank(message = "Company postcode can't be empty.")
    private String postcode;

    @NotBlank(message = "Company country can't be empty.")
    private String country;


    @NotBlank(message = "The company phone number should be provided.")
    private String phoneNumber;

    @NotBlank(message = "A contact name have to be provided.")
    private String ContactName;


    @ValidEmail(message = "A valid mail address is required.")
    private String ContactMail;


    @NotBlank(message = "A valid contact phone number have to be provided.")
    private String ContactPhone;

    @NotNull(message = "A password should be setted.")
    @Length(min = 4, max = 25, message = "The password should contains between 4 and 25 characters.")
    private String password;
    private String matchingPassword;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactMail() {
        return ContactMail;
    }

    public void setContactMail(String contactMail) {
        ContactMail = contactMail;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
