package scotip.app.exceptions;

/**
 * Created by Pierre on 26/04/2016.
 */
public class NoRootModuleException extends Exception {

    public NoRootModuleException() {
        super("There is no root module.");
    }
}

