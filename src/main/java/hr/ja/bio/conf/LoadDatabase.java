package hr.ja.bio.conf;

import hr.ja.bio.model.*;
import hr.ja.bio.model.util.ProjectRoleEnum;
import hr.ja.bio.parser.TaxonomyAbundanceResult;
import hr.ja.bio.parser.TaxonomyAbundanceParser;
import hr.ja.bio.repository.*;
import hr.ja.bio.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
class LoadDatabase {

    @Autowired
    SampleFileRepository taxonomyFileRepository;

    @Autowired
    TaxonAbundanceRepository taxonAbundanceRepository;

    @Autowired
    MyUserDetailsService userService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Value("${bio.load-database}")
    boolean loadDatabase;

    //@Profile("dev")
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (loadDatabase) {
                log.info("init development database");
                Project project = new Project();
                Sample sample = new Sample();

                {

                    User u1 = new User();
                    u1.setUsername("janko");
                    u1.setPassword("ja12345");
                    u1.setEmail("jdiminic@gmail.com");
                    u1.setRole(User.Role.ADMIN);
                    userService.saveUser(u1);

                    sample.setName("sample 1");

                    project.setName("projects 1");


                    projectRepository.save(project);
                    project.addSample(sample);
                    sampleRepository.save(sample);


                    ProjectMember projectMember = new ProjectMember();
                    projectMember.setRole(ProjectRoleEnum.ADMIN);
                    projectMember.setUser(u1);
                    projectMemberRepository.save(projectMember);


                }

                {// parse
                    String path = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example1_metaphlan.txt";
                    TaxonomyAbundanceParser parser = new TaxonomyAbundanceParser(path);
                    TaxonomyAbundanceResult result = parser.parse();

                    SampleFile sampleFile = new SampleFile();
                    sampleFile.setFileName(new File(path).getName());
                    sampleFile.setSample(sample);
                    taxonomyFileRepository.saveAndFlush(sampleFile);


                    for(TaxonomyAbundance ta: result.getTaxonomyAbundances()) {
                        ta.setFile(sampleFile);
                    }
                    taxonAbundanceRepository.saveAll(result.getTaxonomyAbundances());

                }

                log.debug("saved to database");
            }
        };
    }
}