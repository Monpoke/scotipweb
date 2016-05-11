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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @ManyToOne(fetch = FetchType.EAGER)
    protected Line line;


    @OneToMany(mappedBy = "switchboard", cascade = {CascadeType.ALL})
    protected List<Module> modules = new ArrayList<>();


    @OneToMany(mappedBy = "switchboard", cascade = {CascadeType.ALL})
    protected List<CallLog> callLogs = new ArrayList<>();


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

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<CallLog> getCallLogs() {
        return callLogs;
    }

    public String getCallsTotalDuration(){

        int total = 0;

        List<CallLog> callLogs = getCallLogs();
        for (CallLog call :
                callLogs) {
            total += call.getDuration();
        }


        int hours = (int)Math.floor(total / 3600);
        int minutes = (int)Math.floor((total / 60) % 60);
        int seconds = total % 60;

        String s = "";
        if(hours > 0){
            s = hours +"H";
        }

        if(minutes > 0){
            s = minutes +"M";
        }

        s+=seconds+"S";

        return s;
    }

    public Switchboard() {

    }

}