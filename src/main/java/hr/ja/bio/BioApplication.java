package hr.ja.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BioApplication {
    private static final Logger log = LoggerFactory.getLogger(BioApplication.class);

    public static void main(String[] args) {

        log.debug("Started app");

        SpringApplication.run(BioApplication.class, args);
    }
}
