package scotip.app.pages;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import scotip.app.model.Company;

/**
 * Created by Pierre on 18/04/2016.
 */
public abstract class App {


    /**
     * Returns true if someone is logged.
     *
     * @return
     */
    protected boolean isLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object logged = auth.getPrincipal();
            if (logged instanceof UserDetails) {
                // REDIRECT
                return true;
            }
        }
        return false;
    }


    /**
     * Gets the current logged company.
     *
     * @return
     */
    protected Company getCurrentCompany() {
        if (!isLogged()) {
            return null;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Company) auth.getPrincipal();
    }

}
