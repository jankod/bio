package hr.ja.bio.conf;

import hr.ja.bio.model.*;
import hr.ja.bio.model.util.ProjectRole;
import hr.ja.bio.parser.ParseSampleFileException;
import hr.ja.bio.parser.ParserUtil;
import hr.ja.bio.parser.TaxonomyMergedParser;
import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.TaxonomyResult;
import hr.ja.bio.repository.*;
import hr.ja.bio.security.MyUserDetails;
import hr.ja.bio.service.MyUserDetailsService;
import hr.ja.bio.service.TaxNameService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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

    @Autowired
    TaxNameService taxNameService;

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
                    projectMember.setRole(ProjectRole.ADMIN);
                    projectMember.setUser(u1);
                    projectMember.setProject(project);
                    projectMemberRepository.save(projectMember);


                    List<TaxonomyResult> results = parseSampleTaxonomyMerged();
                    insertTaxonomies(results, 1L);

                }

                log.debug("saved to database");
            }
        };
    }

    private static List<TaxonomyResult> parseSampleTaxonomyMerged() throws IOException, ParseSampleFileException {
        FileReader in = new FileReader("C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example_metaphlan_merged.tsv");
        String content = IOUtils.toString(in);
        in.close();
        List<TaxonomyResult> result = ParserUtil.parse(new TaxonomyMergedParser(), content);
        //System.out.println("dobio 0" + result.size());
        return result;
        //insertTaxonomies(result);
    }

    private void insertTaxonomies(List<TaxonomyResult> result, long projectId) {
        Project project = projectRepository.getOne(projectId);
        if(project == null) {
            throw new NullPointerException("project is null " + projectId);
        }

        result.forEach(t -> {
            String sampleName = t.getSampleName();
            Sample sample = sampleRepository.findByName(sampleName);
            if (sample == null) {
                sample = new Sample();
                sample.setName(sampleName);
              //  project.getSamples().add(sample);
                sample.setProject(project);
                sampleRepository.save(sample);
            }
            List<LineageAbundance> lineageAbundances = t.getLineageAbundances();
            for (LineageAbundance l : lineageAbundances) {
                TaxonomyAbundance ta = new TaxonomyAbundance();
                ta.setSample(sample);
                ta.setAbundance(l.getAbundance());

                ILineage lineage = l.getLineage();
                if (lineage.getRank1Kingdom() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank1Kingdom());
                    ta.setRank1Kingdom(id);
                }
                if (lineage.getRank2Phylum() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank2Phylum());
                    ta.setRank2Phylum(id);
                }
                if (lineage.getRank3Class() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank3Class());
                    ta.setRank3Class(id);
                }


                if (lineage.getRank4Order() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank4Order());
                    ta.setRank4Order(id);
                }

                if (lineage.getRank5Family() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank5Family());
                    ta.setRank5Family(id);
                }
                if (lineage.getRank6Genus() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank6Genus());
                    ta.setRank6Genus(id);
                }
                if (lineage.getRank7Species() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank7Species());
                    ta.setRank7Species(id);
                }
                if (lineage.getRank8Strain() != null) {
                    int id = taxNameService.taxNameToInt(lineage.getRank8Strain());
                    ta.setRank8Strain(id);
                }
                taxonAbundanceRepository.save(ta);

            }


        });
    }

    public static void main(String[] args) throws IOException, ParseSampleFileException {
        parseSampleTaxonomyMerged();
    }

}
