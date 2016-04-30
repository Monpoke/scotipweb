package scotip.app.exceptions;

/**
 * Created by Pierre on 26/04/2016.
 */
public class SeveralRootModuleException extends Exception {

    public SeveralRootModuleException() {
        super("There is more than one root module. Aborting.");
    }
}

