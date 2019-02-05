package hr.ja.bio;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    @Autowired
    private DispatcherServlet servlet;

    public static void main(String[] args) {

        // log.debug("Started app");

        SpringApplication.run(App.class, args);
    }


    @Bean
    public CommandLineRunner getCommandLineRunner(ApplicationContext context) {
        servlet.setThrowExceptionIfNoHandlerFound(true);
        return args -> {

        };
    }

    @Bean
    public LayoutDialect layoutDialect() {
        LayoutDialect layoutDialect = new LayoutDialect();
        return layoutDialect;
    }

    @Bean
    public SpringSecurityDialect additionalDialects() {
        return new SpringSecurityDialect();
    }
}
