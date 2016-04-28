package scotip.app.pages.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scotip.app.pages.App;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class WelcomeController extends App {

    @RequestMapping({"/", "/home"})
    public String index() {
        if(isLogged()){
            return "redirect:/u/dashboard";
        }


        return "pages/nonLogged/welcome";
    }
}
