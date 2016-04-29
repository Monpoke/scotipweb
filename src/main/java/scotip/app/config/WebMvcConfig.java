package scotip.app.config;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.spring4.PebbleViewResolver;
import com.mitchellbosecke.pebble.spring4.extension.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scotip.app.converter.SharedLineToLineConverter;
import scotip.app.injections.GlobalVariables;
import scotip.app.tools.CustomViewFunctions;


/**
 * Spring MVC Configuration.
 *
 * @author Craig Walls
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * ALL CONVERTERS
     */
    @Autowired
    SharedLineToLineConverter sharedLineToLineConverter;

    /**
     * FOR SOME FORMS
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(sharedLineToLineConverter);
    }

    /**
     * SOME GLOBALS VARIABLES IN VIEWS
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
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