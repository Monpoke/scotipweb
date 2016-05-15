package scotip.app.exceptions;

/**
 * Created by Pierre on 26/04/2016.
 */
public class ModuleNotFoundException extends Exception {

    public ModuleNotFoundException() {
        super("This module doesn't exists.");
    }
}

