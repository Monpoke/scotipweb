package scotip.app.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class WelcomeController {

    @RequestMapping("/welcome")
    public String index() {
        return "pages/nonLogged/welcome";
    }
}
