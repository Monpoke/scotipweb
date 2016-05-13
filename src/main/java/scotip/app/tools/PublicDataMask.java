package scotip.app.tools;

/**
 * Created by Pierre on 13/05/2016.
 */
public abstract class PublicDataMask {

    public static String getFile(String original){
        if(original==null){
            return "nothing";
        }

        return original;

    }

}
