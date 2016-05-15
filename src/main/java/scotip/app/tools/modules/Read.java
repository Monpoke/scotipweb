package scotip.app.tools.modules;

import scotip.app.model.Module;

/**
 * Created by Pierre on 15/05/2016.
 */
public class Read implements ModuleInterf {

    private Module module;

    public Read(Module module) {
        this.module = module;
    }

    /**
     * Returns a HTML description.
     * @return
     */
    public String textDisplay() {
        String toReturn = "Plays an audio file. Skipping possible.<br />"
                + "<strong>File:</strong> " + module.getSettings().get("file");

        return toReturn;
    }
}
