package scotip.app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "module_model")
public class ModuleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int modelId;

    @Column(name = "slug")
    private String slug;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private boolean enabled = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moduleModel")
    private List<Module> modules = new ArrayList<>();


    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Map<String,Object> getPublicData(){
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("modelId",modelId);
        properties.put("slug",slug);
        properties.put("description",description);
        properties.put("enabled",true);


        return properties;
    }
}
