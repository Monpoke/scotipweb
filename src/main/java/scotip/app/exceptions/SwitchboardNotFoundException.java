package scotip.app.exceptions;

/**
 * Created by Pierre on 26/04/2016.
 */
public class SwitchboardNotFoundException extends NotFoundException
{

    public SwitchboardNotFoundException() {
        super("The switchboard doesn't exists.");
    }
}

