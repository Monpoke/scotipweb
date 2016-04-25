package scotip.app.pages.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class WelcomeController {

    @RequestMapping({"/", "/home"})
    public String index() {
        return "pages/nonLogged/welcome";
    }
}
