package scotip.app.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
@RequestMapping("/loin")
public class LoginController {

    public String index() {
        return "pages/nonLogged/login";
    }
}
