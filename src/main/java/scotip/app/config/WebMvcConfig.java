/*
 * Copyright (c) 2016. Pierre BOURGEOIS
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without restriction,
 *  including without limitation the rights to use, copy, modify, merge,
 *  publish, distribute, sublicense, and/or sell copies of the Software, and
 *  to permit persons to whom the Software is furnished to do so, subject
 *  to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package scotip.app.config;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.spring4.PebbleViewResolver;
import com.mitchellbosecke.pebble.spring4.extension.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scotip.app.converter.OperatorIdToOperatorConverter;
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

    @Autowired
    OperatorIdToOperatorConverter operatorIdToOperatorConverter;

    /**
     * FOR SOME FORMS
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(sharedLineToLineConverter);
        registry.addConverter(operatorIdToOperatorConverter);
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