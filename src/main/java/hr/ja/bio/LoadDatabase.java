package hr.ja.bio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
@Slf4j
class LoadDatabase {


    @Profile("dev")
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("init development database");

        };
    }
}