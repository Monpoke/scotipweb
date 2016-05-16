package scotip.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import scotip.app.config.SpringMvcInitializer;

import java.io.File;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application  extends SpringBootServletInitializer {

    public static final String UPLOAD_DIR = "uploads";


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<Application> applicationClass = Application.class;

    @Bean
    CommandLineRunner init() {
        return (String[] args) -> {
            new File(UPLOAD_DIR).mkdir();
        };
    }

}