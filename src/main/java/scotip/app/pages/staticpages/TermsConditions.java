package scotip.app.pages.staticpages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import scotip.app.infos.PricingPlan;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class TermsConditions {

    @RequestMapping("/terms")
    public String terms() {
        return "pages/static/terms";
    }


    @RequestMapping("/privacy")
    public String privacy() {
        return "pages/static/privacy";
    }

}
