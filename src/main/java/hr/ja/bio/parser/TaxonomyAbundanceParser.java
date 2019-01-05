package hr.ja.bio.parser;


import hr.ja.bio.model.TaxonomyAbundance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
public class TaxonomyAbundanceParser {


    private String path;

    public TaxonomyAbundanceParser(String path) {
        this.path = path;
    }


    public TaxonomyAbundanceResult parse() throws IOException {
        TaxonomyAbundanceResult result = new TaxonomyAbundanceResult();

        try (FileReader input = new FileReader(path)) {
            List<String> lines = IOUtils.readLines(input);
            for (String line : lines) {
                try {
                    if (line.startsWith("#SampleID")) {
                        String[] split = StringUtils.split(line, "\t");
                        String sampleName = split[1].trim();
                        result.setSampleName(sampleName);
                        continue;
                    }
                    if (line.startsWith("#")) {
                        // comment ignore
                        continue;
                    }
                    if (StringUtils.isEmpty(line)) {
                        continue;
                    }
                    String[] split = StringUtils.split(line, "\t");
                    String path = split[0];
                    TaxonomyAbundance tax = new TaxonomyAbundance();

                    result.addTaxonAbundance(tax);

                    tax.setAbundance(Double.parseDouble(split[1].trim()));
                    parseAndAddPath(path, tax);


                } catch (Throwable e) {
                    log.error("Parse error with line '{}'", line, e);
                }
            }
        } catch (IOException e) {
            log.error("", e);
            throw e;
        }
        return result;
    }

    public static void parseAndAddPath(String path, TaxonomyAbundance tax) {
        String[] split = StringUtils.splitByWholeSeparator(path, "|");
        for (String p : split) {
            String rankName = p.substring(3);

//            k__	kingdom
//            p__	phylum
//            c__	class
//            o__	rank4_order
//            f__	rank5_family
//            g__	rank6_genus
//            s__	rank6_species
//            t__	rank7_strain


            if (p.startsWith("k__")) {
                tax.setRank1_kingdom(rankName);
            }
            if (p.startsWith("p__")) {
                tax.setRank2_phylum(rankName);
            }
            if (p.startsWith("c__")) {
                tax.setRank3_class(rankName);
            }
            if (p.startsWith("o__")) {
                tax.setRank4_order(rankName);
            }
            if (p.startsWith("f__")) {
                tax.setRank5_family(rankName);
            }
            if (p.startsWith("g__")) {
                tax.setRank6_genus(rankName);
            }
            if (p.startsWith("s__")) {
                tax.setRank6_species(rankName);
            }
            if (p.startsWith("t__")) {
                tax.setRank7_strain(rankName);
            }
        }

    }


    public static void main(String[] args) throws IOException {
        String path = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example1_metaphlan.txt";
        TaxonomyAbundanceParser parser = new TaxonomyAbundanceParser(path);
        TaxonomyAbundanceResult r = parser.parse();
        log.debug(r.toString());
    }

}
