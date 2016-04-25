package scotip.app.pages.staticpages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class StaticController {

    @RequestMapping("/pricings")
    public String pricings() {
        return "pages/static/prices";
    }


    @RequestMapping("/about")
    public String about() {
        return "pages/static/about";
    }

}
