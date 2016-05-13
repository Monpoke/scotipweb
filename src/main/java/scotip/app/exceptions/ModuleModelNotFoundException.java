package scotip.app.exceptions;

/**
 * Created by Pierre on 26/04/2016.
 */
public class ModuleModelNotFoundException extends Exception {

    public ModuleModelNotFoundException() {
        super("This model doesn't exists or isn't enabled.");
    }
}

