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
import scotip.app.tools.modules.ModOperator;
import scotip.app.tools.modules.ModPlayback;
import scotip.app.tools.modules.ModQueue;
import scotip.app.tools.modules.ModUserInput;

import javax.persistence.*;
import java.util.*;


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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moduleParent", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Module> moduleChilds = new HashSet<>();

    @Column(name = "phone_key")
    private int phoneKey;

    @Column(name = "phoneKeyDisabled")
    private boolean phoneKeyDisabled;

    @Column(name = "description")
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "module_files", joinColumns = @JoinColumn(name = "module_id"))
    @Column(name = "files")
    protected Map<String, String> files = new HashMap<>();


    // MOD OPERATOR
    @ManyToOne(fetch = FetchType.EAGER)
    protected Operator operator;

    // MOD QUEUE
    @ManyToOne(fetch = FetchType.EAGER)
    protected Queue queue;

    // BOTH OPERATOR AND QUEUE
    @ManyToOne(fetch = FetchType.EAGER)
    protected MohGroup mohGroup;


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

    public Set<Module> getModuleChilds() {
        return moduleChilds;
    }

    public void setModuleChilds(Set<Module> moduleChilds) {
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

    public void setRootModule(boolean status){
        if(!status) return;
        setPhoneKey(-1);
        setModuleLevel(1);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPhoneKeyDisabled() {
        return phoneKeyDisabled;
    }

    public void setPhoneKeyDisabled(boolean phoneKeyDisabled) {
        this.phoneKeyDisabled = phoneKeyDisabled;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public MohGroup getMohGroup() {
        return mohGroup;
    }

    public void setMohGroup(MohGroup mohGroup) {
        this.mohGroup = mohGroup;
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
        properties.put("phoneKeyDisabled", isPhoneKeyDisabled());
        properties.put("phoneKey", phoneKey);
        properties.put("description", description);
        properties.put("settings", getPublicSettings());
        properties.put("files", getFiles());

        if(operator!=null){
            properties.put("operator",operator.getOid());
        }

        if(queue!=null){
            properties.put("queue",queue.getQid());
        }

        if(mohGroup!=null){
            properties.put("moh",mohGroup.getGroupId());
        }

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

        if (realSettings.containsKey("skip")) {
            sett.put("skip", realSettings.get("skip"));
        }


        // USERINPUT
        if (realSettings.containsKey("variable")) {
            sett.put("variable", realSettings.get("variable"));
        }
        if (realSettings.containsKey("uri")) {
            sett.put("uri", realSettings.get("uri"));
        }
        if (realSettings.containsKey("numberFormatMin")) {
            sett.put("numberFormatMin", realSettings.get("numberFormatMin"));
        }
        if (realSettings.containsKey("numberFormatMax")) {
            sett.put("numberFormatMax", realSettings.get("numberFormatMax"));
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
                retu = new ModPlayback(this).textDisplay();
                break;
            case "userinput":
                retu = new ModUserInput(this).textDisplay();
                break;
            case "operator":
                retu = new ModOperator(this).textDisplay();
                break;
            case "queue":
                retu = new ModQueue(this).textDisplay();
                break;
            default:
                retu = "Unknown module.";
        }


        return retu;
    }
}
