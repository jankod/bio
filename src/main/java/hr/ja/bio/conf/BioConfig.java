package hr.ja.bio.conf;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Data
@ConfigurationProperties(prefix = "bio")
@Validated
public class BioConfig {


    @NotNull
    String uploadDir;

}
