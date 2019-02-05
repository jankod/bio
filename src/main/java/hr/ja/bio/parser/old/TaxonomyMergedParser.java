package hr.ja.bio.parser.old;

import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.TaxonomyResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TaxonomyMergedParser {

	private String content;

	public TaxonomyMergedParser(String content) {
		this.content = content;
	}

	public TaxonomyMergedResult parse() throws IOException {
		TaxonomyMergedResult result = new TaxonomyMergedResult();

		Map<Integer, TaxonomyResult> res = new HashMap<>();
		String[] lines = StringUtils.split(content, "\n");

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
						res.put(i, new TaxonomyResult(sampleName));
					}
					continue;
				}

				String[] split = StringUtils.split(line, "\t");
				String path = split[0];
				for (int i = 1; i < split.length; i++) {
					LineageAbundance oneTax = new LineageAbundance();
					oneTax.setAbundance(Double.parseDouble(split[i].trim()));
					if (oneTax.getAbundance() > 0) {
						TaxonomyParser.parseAndAddPath(path, oneTax);
						res.get(i).addTaxonAbundance(oneTax);
					}
				}

			} catch (Throwable e) {
				log.error("Parse error with line '{}'", line, e);
			}
		}
		result.setTaxonomyParseResults(new ArrayList<>(res.values()));

		return result;
	}

	public static void main(String[] args) throws IOException {
		TaxonomyMergedParser p = new TaxonomyMergedParser(
				"C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example_metaphlan_merged.tsv");
		TaxonomyMergedResult result = p.parse();
		int total = 0;
		for (TaxonomyResult tr : result.taxonomyParseResults) {
			total += tr.getLineageAbundances().size();
			log.debug("{} {}", tr.getSampleName(), tr.getLineageAbundances().size());
			for (LineageAbundance t : tr.getLineageAbundances()) {
			}
		}

		log.debug("total {}", total);

	}

}
