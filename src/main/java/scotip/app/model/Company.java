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

package scotip.app.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.*;


/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "company")
public class Company implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "country")
    private String country;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "contactName")
    private String ContactName;

    @Column(name = "ContactMail", unique = true)
    private String ContactMail;

    @Column(name = "ContactPhone")
    private String ContactPhone;

    @Column(name = "registrationDate")
    private Date registrationDate;

    @Column(name = "password")
    private String password;


    @Column(name = "STATE", nullable = false)
    private String state = State.ACTIVE.getState();

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "company_profiles",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "profile_id")})
    private Set<CompanyProfile> companyProfiles = new HashSet<>();


    /**
     * @TODO HACK Fetch LAZY
     */
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SELECT)
    private List<Switchboard> switchboards = new ArrayList<>();

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SELECT)
    private List<Operator> operators = new ArrayList<>();



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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


    @Transient
    private String tmpMD5_mail= null;

    public String getMD5CryptedMail() {
        if(tmpMD5_mail==null) {
            try {
                byte[] bytesOfMessage = getContactMail().getBytes();

                MessageDigest md = MessageDigest.getInstance("MD5");
                md.reset();
                byte[] thedigest = md.digest(bytesOfMessage);


                //Decoding
                BigInteger bigInt = new BigInteger(1, thedigest);
                String hashtext = bigInt.toString(16);
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
                tmpMD5_mail=hashtext;
            } catch (Exception e) {
                return "none";
            }

        }
        return tmpMD5_mail;

    }

    public String getState() {
        return state;
    }

    public Set<CompanyProfile> getCompanyProfiles() {
        return companyProfiles;
    }

    public String getAvatar() {
        return "https://gravatar.com/avatar/" + getMD5CryptedMail();
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }


    public String getPassword() {
        return password;
    }


    /**
     * Get authorities
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> objects = new ArrayList<>();
        objects.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
        return objects;
    }

    @Override
    public String getUsername() {
        return getContactMail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCompanyProfiles(Set<CompanyProfile> companyProfiles) {
        this.companyProfiles = companyProfiles;
    }

    public void setSwitchboards(List<Switchboard> switchboards) {
        this.switchboards = switchboards;
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }

    public List<Switchboard> getSwitchboards() {
        return switchboards;
    }

    public Company() {
        setRegistrationDate(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (getId() != company.getId()) return false;
        if (getName() != null ? !getName().equals(company.getName()) : company.getName() != null) return false;
        if (getAddress() != null ? !getAddress().equals(company.getAddress()) : company.getAddress() != null)
            return false;
        if (getAddress2() != null ? !getAddress2().equals(company.getAddress2()) : company.getAddress2() != null)
            return false;
        if (getCity() != null ? !getCity().equals(company.getCity()) : company.getCity() != null) return false;
        if (getPostcode() != null ? !getPostcode().equals(company.getPostcode()) : company.getPostcode() != null)
            return false;
        if (getCountry() != null ? !getCountry().equals(company.getCountry()) : company.getCountry() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(company.getPhoneNumber()) : company.getPhoneNumber() != null)
            return false;
        if (getContactName() != null ? !getContactName().equals(company.getContactName()) : company.getContactName() != null)
            return false;
        if (getContactMail() != null ? !getContactMail().equals(company.getContactMail()) : company.getContactMail() != null)
            return false;
        if (getContactPhone() != null ? !getContactPhone().equals(company.getContactPhone()) : company.getContactPhone() != null)
            return false;
        if (getRegistrationDate() != null ? !getRegistrationDate().equals(company.getRegistrationDate()) : company.getRegistrationDate() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(company.getPassword()) : company.getPassword() != null)
            return false;
        return getState() != null ? getState().equals(company.getState()) : company.getState() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getAddress2() != null ? getAddress2().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getPostcode() != null ? getPostcode().hashCode() : 0);
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getContactName() != null ? getContactName().hashCode() : 0);
        result = 31 * result + (getContactMail() != null ? getContactMail().hashCode() : 0);
        result = 31 * result + (getContactPhone() != null ? getContactPhone().hashCode() : 0);
        result = 31 * result + (getRegistrationDate() != null ? getRegistrationDate().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        return result;
    }
}
