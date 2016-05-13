package scotip.app.exceptions;

/**
 * Created by Pierre on 26/04/2016.
 */
public class OperationException extends Exception
{
    public OperationException() {
        super("Operation not supported.");
    }

    public OperationException(String message) {
        super(message);
    }

    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

