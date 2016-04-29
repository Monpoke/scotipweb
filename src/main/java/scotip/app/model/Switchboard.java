package scotip.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "switchboard")
public class Switchboard {

    @Id
    @GeneratedValue
    @Column(name = "sid")
    protected int sid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    protected Company company;

    @Column(name = "phoneCodeAccess")
    protected int phoneCodeAccess;

    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;


    @Column(name = "enabled")
    protected boolean enabled = true;

    @Column(name = "deleted")
    protected boolean deleted = false;


    @ManyToOne(fetch=FetchType.EAGER)
    protected Line line;





    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public int getPhoneCodeAccess() {
        return phoneCodeAccess;
    }

    public void setPhoneCodeAccess(int phoneCodeAccess) {
        this.phoneCodeAccess = phoneCodeAccess;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Switchboard(){

    }

}