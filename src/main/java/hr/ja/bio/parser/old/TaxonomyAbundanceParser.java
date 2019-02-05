package hr.ja.bio.parser.old;


import java.io.IOException;
import java.util.Scanner;

import hr.ja.bio.parser.model.LineageAbundance;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaxonomyAbundanceParser {


    private String content;

    public TaxonomyAbundanceParser(String content) {
        this.content = content;
    }


    public TaxonomyAbundanceResult parse() throws IOException {
        TaxonomyAbundanceResult result = new TaxonomyAbundanceResult();
        Scanner scanner = new Scanner(content);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
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


                tax.setAbundance(Double.parseDouble(split[1].trim()));
                parseAndAddPath(path, tax);
                
                result.addTaxonAbundance(tax);


            } catch (Throwable e) {
                log.error("Parse error with line '{}'", line, e);
            }
        }
        
        return result;
    }

    public static void parseAndAddPath(String path, LineageAbundance tax) {
        String[] split = StringUtils.splitByWholeSeparator(path, "|");
        for (String p : split) {
            String rankName = p.substring(3);
            
            if (p.startsWith("k__")) {
            	
                tax.getLineage().setRank1Kingdom(rankName);
            }
            if (p.startsWith("p__")) {
            	tax.getLineage().setRank2Phylum(rankName);
            }
            if (p.startsWith("c__")) {
            	tax.getLineage().setRank3Class(rankName);
            }
            if (p.startsWith("o__")) {
            	tax.getLineage().setRank4Order(rankName);
            }
            if (p.startsWith("f__")) {
            	tax.getLineage().setRank5Family(rankName);
            }
            if (p.startsWith("g__")) {
            	tax.getLineage().setRank6Genus(rankName);
            }
            if (p.startsWith("s__")) {
            	tax.getLineage().setRank7Species(rankName);
            }
            if (p.startsWith("t__")) {
            	tax.getLineage().setRank8Strain(rankName);
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
