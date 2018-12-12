package hr.ja.bio.parser;

import hr.ja.bio.model.TaxonAbundance;
import hr.ja.bio.model.TaxonomyFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class TaxonomyMetaphlanParser {
    private static final Logger log = LoggerFactory.getLogger(TaxonomyMetaphlanParser.class);


    private String path;

    public TaxonomyMetaphlanParser(String path) {
        this.path = path;
    }


    public TaxonomyFile parse() throws FileNotFoundException {

        TaxonomyFile file = new TaxonomyFile();

        try (FileReader input = new FileReader(path)) {
            List<String> lines = IOUtils.readLines(input);
            for (String line : lines) {
                try {
                    if (line.startsWith("#SampleID")) {
                        String[] split = StringUtils.split(line, "\t");
                        file.setIdName(split[1].trim());
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
                    TaxonAbundance tax = new TaxonAbundance();
                    file.addTaxonAbundance(tax);
                    parseAndAddPath(path, tax);
                    tax.setAbundance(Double.parseDouble(split[1].trim()));


                } catch (Throwable e) {
                    log.error("Parse error with line '{}'", line, e);
                }
            }
        } catch (IOException e) {
            log.error("", e);
        }
        return file;
    }

    private void parseAndAddPath(String path, TaxonAbundance tax) {
        String[] split = StringUtils.splitByWholeSeparator(path, "|");
        for (String p : split) {
            String rankName = p.substring(3);

//            k__	kingdom
//            p__	phylum
//            c__	class
//            o__	order
//            f__	family
//            g__	genus
//            s__	species
//            t__	strain


            if (p.startsWith("k__")) {
                tax.setKingdom(rankName);
            }
            if (p.startsWith("p__")) {
                tax.setPhylum(rankName);
            }
            if (p.startsWith("c__")) {
                tax.setClass_taxon(rankName);
            }
            if (p.startsWith("o__")) {
                tax.setOrder(rankName);
            }
            if (p.startsWith("f__")) {
                tax.setFamily(rankName);
            }
            if (p.startsWith("g__")) {
                tax.setGenus(rankName);
            }
            if (p.startsWith("s__")) {
                tax.setSpecies(rankName);
            }
            if (p.startsWith("t__")) {
                tax.setStrain(rankName);
            }
        }

    }


    public static void main(String[] args) throws FileNotFoundException {
        String path = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example1_metaphlan.txt";
        TaxonomyMetaphlanParser parser = new TaxonomyMetaphlanParser(path);
        TaxonomyFile taxonomyFile = parser.parse();
        log.debug(taxonomyFile.toString());
    }

}
