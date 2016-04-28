package scotip.app.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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





    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "switchboard_line", joinColumns = {
            @JoinColumn(name = "switchboard_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "line_id",
                    nullable = false, updatable = false)})
    private Set<Line> lines = new HashSet<>(0);

    public Set<Line> getLines() {
        return this.lines;
    }




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

    public void setLines(Set<Line> lines) {
        this.lines = lines;
    }

    public void addLine(Line l){
        lines.add(l);
    }

    public Switchboard(){

    }

}