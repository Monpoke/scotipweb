package scotip.app.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "company")
public class Company {

    @Id @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name ="name")
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

    @Column(name = "salt")
    private  String salt;

    @Column(name = "password")
    private  String password;


    @Column(name="STATE", nullable=false)
    private String state=State.ACTIVE.getState();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "company_profiles",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "profile_id") })
    private Set<CompanyProfile> companyProfiles = new HashSet<>();


  /*  @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private Set<Switchboard> switchboards = new HashSet<>();
*/


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

    public String getMD5CryptedMail(){
        try {
            byte[] bytesOfMessage = getContactMail().getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            return thedigest.toString();
        } catch(Exception e){
            return "none";
        }

    }

    public String getState() {
        return state;
    }

    public Set<CompanyProfile> getCompanyProfiles() {
        return companyProfiles;
    }

    public String getAvatar(){
        return "http://gravatar.com/avatar/" + getMD5CryptedMail();
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

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlainPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        setPassword(hashPassword(password));
    }

    public boolean isGoodPassword(String pass){
        String password = null;
        try {
            password = hashPassword(pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return password.equals(getPassword());}


    private  String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);

        /*
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt().getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return  toHex(salt) + toHex(hash);*/
    }

    private  String getSalt() throws NoSuchAlgorithmException
    {
        if(this.salt == null || this.salt == ""){
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            this.salt = salt.toString();
        }
        return this.salt;
    }

    private  String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

  /*  public Set<Switchboard> getSwitchboards() {
        return switchboards;
    }
*/
    public Company(){
        setRegistrationDate(new Date());
    }

}
