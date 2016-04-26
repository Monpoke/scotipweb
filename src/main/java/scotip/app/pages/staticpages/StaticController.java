package scotip.app.pages.staticpages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import scotip.app.infos.PricingPlan;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class StaticController {

    @RequestMapping("/pricings")
    public String pricings(ModelMap model) {
        model.put("plans", PricingPlan.getCurrentPlans());

        return "pages/static/prices";
    }


    @RequestMapping("/about")
    public String about() {
        return "pages/static/about";
    }

}
