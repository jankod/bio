package hr.ja.bio.parser;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.TaxonomyResult;

public class TaxonomyMergedParser implements IFileParser<List<TaxonomyResult>> {
	List<TaxonomyResult> result = new ArrayList<>();

	@Override
	public void parseLine(String line, int lineNumber) {
		if (lineNumber == 0) {
			List<String> samplesName = parseTaxonomyMeregedHeader(line);
			for (String name : samplesName) {
				result.add(new TaxonomyResult(name));
			}
			return;
		}

		String[] split = StringUtils.split(line, "\t");
		String linagePart = split[0];
		for (int i = 1; i < split.length; i++) {
			double abundance = Double.parseDouble(split[i]);
			if (abundance != 0) {
				ILineage lineage = ParserUtil.parseLineage(linagePart, "|");
				result.get(i - 1).addTaxonAbundance(new LineageAbundance(abundance, lineage));
			}
		}
	}

	@Override
	public boolean filterLine(String line, int lineNumber) {
		if (lineNumber == 0) {
			return false;
		}
		if (isEmpty(line) || line.startsWith("#")) {
			return false;
		}
		return false;
	}

	@Override
	public List<TaxonomyResult> getResult() {
		return result;
	}

	public List<String> parseTaxonomyMeregedHeader(String line) {
		// SampleID CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6
		String[] split = StringUtils.splitByWholeSeparator(line, "\t");
		ArrayList<String> result = new ArrayList<String>(split.length - 1);
		for (int i = 1; i < split.length; i++) {
			result.add(split[i].trim());
		}
		return result;
	}

}
