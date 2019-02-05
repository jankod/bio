package hr.ja.bio.parser.old;


import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.TaxonomyResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
public class TaxonomyParser {


    private String path;
    private List<String> lines;

    public TaxonomyParser(File path) {
        this.path = path.getAbsolutePath();
    }

    public TaxonomyParser(List<String> lines) {
        this.lines = lines;
    }


    public TaxonomyResult parse() throws IOException {
        TaxonomyResult result = new TaxonomyResult();

        if (path != null) {
            try (FileReader input = new FileReader(path)) {
                List<String> lines = IOUtils.readLines(input);
                parseLines(result, lines);
            } catch (IOException e) {
                log.error("", e);
                throw e;
            }
        } else {

            parseLines(result, lines);
        }
        return result;
    }

    private void parseLines(TaxonomyResult result, List<String> lines) {
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
                LineageAbundance tax = new LineageAbundance();

                result.getLineageAbundances().add(tax);

                tax.setAbundance(Double.parseDouble(split[1].trim()));
                parseAndAddPath(path, tax);


            } catch (Throwable e) {
                log.error("Parse error with line '{}'", line, e);
                throw e;
            }
        }
    }



    public static void parseAndAddPath(String path, LineageAbundance tax) {
        String[] split = StringUtils.splitByWholeSeparator(path, "|");
        for (String p : split) {
            String rankName = p.substring(3);

//            if (p.startsWith("k__")) {
//                tax.(rankName);
//            }
//            if (p.startsWith("p__")) {
//                tax.setRank2Phylum(rankName);
//            }
//            if (p.startsWith("c__")) {
//                tax.setRank3Class(rankName);
//            }
//            if (p.startsWith("o__")) {
//                tax.setRank4Order(rankName);
//            }
//            if (p.startsWith("f__")) {
//                tax.setRank5Family(rankName);
//            }
//            if (p.startsWith("g__")) {
//                tax.setRank6Genus(rankName);
//            }
//            if (p.startsWith("s__")) {
//                tax.setRank7Species(rankName);
//            }
//            if (p.startsWith("t__")) {
//                tax.setRank8Strain(rankName);
//            }
        }
    }


    public static void main(String[] args) throws IOException {
        String path = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example1_metaphlan.txt";
        FileInputStream input = new FileInputStream(new File(path));
        TaxonomyParser parser = new TaxonomyParser(IOUtils.readLines(input, "UTF-8"));
        input.close();
        TaxonomyResult r = parser.parse();

        log.debug(r.toString());
    }

}
