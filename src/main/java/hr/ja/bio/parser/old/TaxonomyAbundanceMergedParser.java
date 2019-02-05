package hr.ja.bio.parser.old;


import hr.ja.bio.parser.model.LineageAbundance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TaxonomyAbundanceMergedParser {


    private String path;

    public TaxonomyAbundanceMergedParser(String path) {
        this.path = path;
    }


    public TaxonomyAbundanceMergedResult parse() throws IOException {
        TaxonomyAbundanceMergedResult result = new TaxonomyAbundanceMergedResult();

        Map<Integer, TaxonomyAbundanceResult> res = new HashMap<>();
        try (FileReader input = new FileReader(path)) {
            List<String> lines = IOUtils.readLines(input);


            for (String line : lines) {
                try {

                    if (line.isEmpty()) {
                        continue;
                    }
                    if (line.startsWith("#")) {
                        // comment ignore
                        continue;
                    }
                    if (line.startsWith("SampleID")) { // First Line
                        String[] split = StringUtils.split(line, "\t");
                        for (int i = 1; i < split.length; i++) {
                            String sampleName = split[i];
                            res.put(i, new TaxonomyAbundanceResult(sampleName));
                        }
                        continue;
                    }

                    String[] split = StringUtils.split(line, "\t");
                    String path = split[0];
                    for (int i = 1; i < split.length; i++) {
                        LineageAbundance oneTax = new LineageAbundance();
                        oneTax.setAbundance(Double.parseDouble(split[i].trim()));
                        if (oneTax.getAbundance() > 0) {
                            TaxonomyAbundanceParser.parseAndAddPath(path, oneTax);
                            res.get(i).addTaxonAbundance(oneTax);
                        }
                    }

                } catch (Throwable e) {
                    log.error("Parse error with line '{}'", line, e);
                }
            }
        }
        result.setTaxonomyParseResults(new ArrayList<>( res.values()));

        return result;
    }

    public static void main(String[] args) throws IOException {
        TaxonomyAbundanceMergedParser p = new TaxonomyAbundanceMergedParser("C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example_metaphlan_merged.tsv");
        TaxonomyAbundanceMergedResult re = p.parse();
        log.debug("finish");


    }

}
