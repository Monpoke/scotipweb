package scotip.app.model;

import javax.persistence.*;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "moh_group")
public class MohGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mohgroup_id")
    private int groupdId;

    @Column(name = "group_name")
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "switchboard_id", nullable = false)
    protected Switchboard switchboard;

    public int getGroupdId() {
        return groupdId;
    }

    public void setGroupdId(int groupdId) {
        this.groupdId = groupdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Switchboard getSwitchboard() {
        return switchboard;
    }

    public void setSwitchboard(Switchboard switchboard) {
        this.switchboard = switchboard;
    }
}
