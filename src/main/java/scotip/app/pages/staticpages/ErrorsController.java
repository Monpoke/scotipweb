package scotip.app.pages.staticpages;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Pierre on 24/04/2016.
 */
@Controller
public class ErrorsController {
    String path = "pages/errors/";

    public String error400() {
        System.out.println("custom error handler");
        return path + "/400";
    }

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error404() {
        System.out.println("custom error handler");
        return path + "/404";
    }

    @RequestMapping(value = "/500")
    public String error500() {
        System.out.println("custom error handler");
        return path + "/500";
    }

}
