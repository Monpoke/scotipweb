package scotip.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "module_model")
public class ModuleModel {

    @Id @GeneratedValue
    @Column(name="model_id")
    private int modelId;

    @Column(name="slug")
    private String slug;

    @Column(name="description")
    private String description;

    @Column(name="enabled")
    private boolean enabled = false;

    @OneToMany(fetch=FetchType.LAZY,mappedBy="moduleModel")
    private List<Module> modules= new ArrayList<>();


}
