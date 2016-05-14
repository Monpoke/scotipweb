package scotip.app.model;

import javax.persistence.*;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "moh_file")
public class MohFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moh_id")
    private int soundId;

    @Column(name = "moh_path")
    private String path;

    @Column(name = "moh_name")
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    protected MohGroup group;

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MohGroup getGroup() {
        return group;
    }

    public void setGroup(MohGroup group) {
        this.group = group;
    }
}
