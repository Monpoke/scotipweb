package scotip.app.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue
    @Column(name = "line_id")
    private int lineId;

    @Column(name = "country")
    private String country;

    @Column(name = "regionCode")
    private int regionCode;

    @Column(name = "number", unique = true)
    private String number;

    @Column(name = "shared")
    private boolean shared = true;

    // This end is not the owner. It's the inverse of the User.roles association
    @ManyToMany(mappedBy = "lines")
    private Set<Switchboard> users = new HashSet<>();


    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public int getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(int regionCode) {
        this.regionCode = regionCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Format a number in international format
     * @return
     */
    public String formatNumber() {

        String nb = "" + getNumber();

        char firstChar = nb.charAt(0);

        // delete the first character
        nb = nb.substring(1);
        String fin = "";
        for (int i = 0, totalLength= nb.length(); i < totalLength; i++) {
        fin += nb.charAt(i);
            if(i%2==0 && totalLength-i > 2){
                fin+=".";
            }
        }

        return "+" + getRegionCode() + "(" + firstChar + ")"+fin;
    }

    public Set<Switchboard> getUsers() {
        return users;
    }

    public void setUsers(Set<Switchboard> users) {
        this.users = users;
    }

    public Line(){
    }

    public Line(int regionCode, String number, boolean shared) {
        this.regionCode = regionCode;
        this.number = number;
        this.shared = shared;
    }


}
