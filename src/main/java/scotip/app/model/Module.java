package scotip.app.model;

import javax.persistence.*;
import java.util.List;


/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "module")
public class Module {

    @Id @GeneratedValue
    @Column(name = "mid")
    private int mid;

    @Column(name = "module_level")
    private int moduleLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    private Module moduleParent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moduleParent")
    private List<Module> moduleChilds;

    @Column(name = "phone_key")
    private int phoneKey;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "switchboard_id", nullable = false)
    protected Switchboard switchboard;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    protected ModuleModel moduleModel;


    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getModuleLevel() {
        return moduleLevel;
    }

    public void setModuleLevel(int moduleLevel) {
        this.moduleLevel = moduleLevel;
    }

    public int getPhoneKey() {
        return phoneKey;
    }

    public void setPhoneKey(int phoneKey) {
        this.phoneKey = phoneKey;
    }

    public Module getModuleParent() {
        return moduleParent;
    }

    public void setModuleParent(Module moduleParent) {
        this.moduleParent = moduleParent;
    }

    public Switchboard getSwitchboard() {
        return switchboard;
    }

    public void setSwitchboard(Switchboard switchboard) {
        this.switchboard = switchboard;
    }

    public ModuleModel getModuleModel() {
        return moduleModel;
    }

    public void setModuleModel(ModuleModel moduleModel) {
        this.moduleModel = moduleModel;
    }

    public List<Module> getModuleChilds() {
        return moduleChilds;
    }

    public void setModuleChilds(List<Module> moduleChilds) {
        this.moduleChilds = moduleChilds;
    }
}
