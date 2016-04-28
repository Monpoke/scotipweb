package scotip.app.model;

import javax.persistence.*;


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

    @Column(name = "phone_key")
    private int phoneKey;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "switchboard_id", nullable = false)
    protected Switchboard switchboard;


    @ManyToOne(fetch = FetchType.LAZY)
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
}
