package hr.ja.bio.conf;

import hr.ja.bio.model.TaxonomyFile;
import hr.ja.bio.parser.TaxonomyMetaphlanParser;
import hr.ja.bio.repository.TaxonomyFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
class LoadDatabase {

    @Autowired
    TaxonomyFileRepository repo;


    boolean initDatabase = false;

    //@Profile("dev")
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            if (initDatabase) {
                log.info("init development database");
                String path = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example1_metaphlan.txt";
                TaxonomyMetaphlanParser parser = new TaxonomyMetaphlanParser(path);
                TaxonomyFile taxonomyFile = parser.parse();
                taxonomyFile.setFileName(new File(path).getName());
                repo.saveAndFlush(taxonomyFile);
                log.debug("saved to database");
            }
        };
    }
}