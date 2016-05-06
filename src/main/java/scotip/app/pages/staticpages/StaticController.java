package scotip.app.pages.staticpages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import scotip.app.dao.switchboard.SwitchboardDao;
import scotip.app.infos.PricingPlan;
import scotip.app.dao.switchboard.SwitchboardDaoImpl;
import scotip.app.service.switchboard.SwitchboardService;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class StaticController {

    @Autowired
    private SwitchboardService service;

    @RequestMapping("/pricings")
    public String pricings(ModelMap model) {
        model.put("plans", PricingPlan.getCurrentPlans());

        return "pages/static/prices";
    }


    @RequestMapping("/about")
    public String about() {
        return "pages/static/about";
    }


    @RequestMapping("/directory")
    public String directory(ModelMap model) {
        model.put("list", service.getAllSwitchboard());

        return "pages/static/directory";
    }
}
