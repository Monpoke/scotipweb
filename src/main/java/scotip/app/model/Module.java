/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package scotip.app.model;

import com.google.gson.Gson;
import scotip.app.tools.PublicDataMask;
import scotip.app.tools.modules.Playback;
import scotip.app.tools.modules.Read;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Pierre on 21/04/2016.
 */
@Entity
@Table(name = "module")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private int mid;

    @Column(name = "module_level")
    private int moduleLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    private Module moduleParent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moduleParent", cascade = {CascadeType.ALL})
    private List<Module> moduleChilds = new ArrayList<>();

    @Column(name = "phone_key")
    private int phoneKey;

    @Column(name="description")
    private String description = "";


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "switchboard_id", nullable = false)
    protected Switchboard switchboard;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    protected ModuleModel moduleModel;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "module_settings", joinColumns = @JoinColumn(name = "module_id"))
    @Column(name = "setting")
    protected Map<String, String> settings = new HashMap<>();


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

    public void addChildModule(Module module) {
        this.moduleChilds.add(module);
        module.setModuleParent(this);
    }


    public Map<String, String> getSettings() {
        return settings;
    }

    public void setModuleSetting(String name, String value) {
        settings.put(name, value);
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    public boolean isRootModule() {
        return (getPhoneKey() == -1 && getModuleLevel() <= 1);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Module() {

    }

    public Module(Switchboard switchboard) {
        setSwitchboard(switchboard);
    }


    @Override
    public String toString() {
        return "Module{" +
                "mid=" + mid +
                ", moduleLevel=" + moduleLevel +
                ", moduleParent=" + moduleParent +
                ", moduleChilds=" + moduleChilds +
                ", phoneKey=" + phoneKey +
                ", switchboard=" + switchboard +
                ", moduleModel=" + moduleModel +
                ", settings=" + settings +
                '}';
    }

    public Map<String, Object> getPublicData() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("mid", mid);
        properties.put("sid", getSwitchboard().getSid());
        properties.put("root", isRootModule());
        properties.put("phoneKey", phoneKey);
        properties.put("description", description);
        properties.put("settings", getPublicSettings());

        return properties;
    }

    /**
     * Have to get all public settings.
     *
     * @return
     */
    private Map<String, String> getPublicSettings() {
        Map<String, String> sett = new HashMap<>();

        Map<String, String> realSettings = getSettings();

        if (realSettings.containsKey("file")) {
            sett.put("file", PublicDataMask.getFile(realSettings.get("file")));
        }

        return sett;
    }

    public String toJson() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("module", getPublicData());
        properties.put("model", getModuleModel().getPublicData());
        properties.put("parent", getModuleParent() == null ? false : getModuleParent().getPublicData());


        return new Gson().toJson(properties);
    }


    /**
     * Returns the text case.
     *
     * @return
     */
    public String display() {
        String retu;
        switch (getModuleModel().getSlug()) {
            case "playback":
                retu = new Playback(this).textDisplay();
                break;
            case "read":
                retu = new Read(this).textDisplay();
                break;
            default:
                retu = "Unknown module.";
        }


        return retu;
    }
}
