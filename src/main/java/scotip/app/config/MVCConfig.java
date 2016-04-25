package scotip.app.config;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.spring4.PebbleViewResolver;
import com.mitchellbosecke.pebble.spring4.extension.SpringExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scotip.app.injections.GlobalVariables;
import scotip.app.tools.CustomViewFunctions;


@Configuration
@ComponentScan()
@EnableAutoConfiguration
public class MVCConfig extends WebMvcConfigurerAdapter {

    /**
     * Register some interceptors.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new GlobalVariables());
    }

    /**
     * =======================
     * VIEW RESOLVER
     * =======================
     */
    @Bean
    public Loader templateLoader() {
        return new ClasspathLoader();
    }

    @Bean
    public SpringExtension springExtension() {
        return new SpringExtension();
    }

    @Bean
    public PebbleEngine pebbleEngine() {
        return new PebbleEngine.Builder()
                .loader(templateLoader())
                .extension(springExtension())
                .extension(getTypeObject())
                .build();
    }

    private Extension getTypeObject() {
        return new CustomViewFunctions();
    }

    @Bean()
    public ViewResolver viewResolver() {
        PebbleViewResolver viewResolver = new PebbleViewResolver();
        viewResolver.setPrefix("templates/");
        viewResolver.setSuffix(".twig");
        viewResolver.setPebbleEngine(pebbleEngine());


        return viewResolver;
    }


}