package scotip.app.pages.visitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class SecurityController {


    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;


    @RequestMapping("/login")
    public String index() {
        return "pages/nonLogged/login";
    }


    @RequestMapping("/loginA")
    @ResponseBody
    public String doAjaxLogin(@RequestParam("mail") String mail, @RequestParam("pass") String pass) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mail, pass);
        try {
            UserDetails details = userDetailsService.loadUserByUsername(mail);
            token.setDetails(details);

            try {
                Authentication auth = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                return "@/u/dashboard";
            } catch (BadCredentialsException e) {
                return "error";
            }
        } catch (UsernameNotFoundException une) {
            return "error";
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/?logout";
    }
}
