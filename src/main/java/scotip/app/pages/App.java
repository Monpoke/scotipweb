package scotip.app.pages;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pierre on 18/04/2016.
 */
public abstract class App {


    protected void redirectIfLogged(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            Object logged = auth.getPrincipal();
            if(logged instanceof UserDetails){
                // REDIRECT

            }
        }
    }

}
