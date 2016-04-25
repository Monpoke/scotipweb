package scotip.app.injections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import scotip.app.model.Company;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GlobalVariables extends HandlerInterceptorAdapter {


    /**
     * To add some variables to view.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            ModelMap model = modelAndView.getModelMap();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();


            // Login
            Company currentLoggedCompany = null;
            boolean isLogged = false;
            String currentUsername = "unknown";
            if (auth != null) {

                Object principal = auth.getPrincipal();

                if (principal instanceof UserDetails) {
                    currentUsername = ((UserDetails) principal).getUsername();
                    currentLoggedCompany = (Company)principal;
                    isLogged=true;
                } else {
                    currentUsername = principal.toString();
                }

            }


            model.put("isLogged", isLogged);
            model.put("currentUsername", currentUsername);
            model.put("currentCompany", currentLoggedCompany);

        }
    }
}