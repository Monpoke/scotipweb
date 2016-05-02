package scotip.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Pierre on 25/04/2016.
 */

@Configuration
@ComponentScan(basePackages = "scotip.app")
@PropertySource("classpath:application.properties")
public class MainConfig {

    public final static String NODESPAS_URL = "http://localhost:8000";

}
