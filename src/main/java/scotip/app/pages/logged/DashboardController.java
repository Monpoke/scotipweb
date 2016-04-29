package scotip.app.pages.logged;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Pierre on 18/04/2016.
 */
@Controller
public class DashboardController {


    @RequestMapping({"/u","/u/dashboard"})
    protected String doGet() {

        return ("pages/static/index");
    }


}
