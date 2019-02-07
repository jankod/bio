package hr.ja.bio.parser;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.TaxonomyResult;

public class TaxonomyParser implements IFileParser<TaxonomyResult> {
	TaxonomyResult result = new TaxonomyResult();

	@Override
	public void parseLine(String line, int lineNumber) {
		if (lineNumber == 0) {
			String sampleName = parseTaxonomyHeader(line);
			result.setSampleName(sampleName);
			return;
		}

		String[] split = StringUtils.split(line, "\t");

		ILineage lineage = ParserUtil.parseLineage(split[0], "|");
		Double abundance = Double.parseDouble(split[1]);
		result.addTaxonAbundance(new LineageAbundance(abundance, lineage));

	}

	@Override
	public boolean filterLine(String line, int lineNumber) {
		if (lineNumber == 0) {
			return false;
		}
		if (isEmpty(line) || line.startsWith("#")) {
			return true;
		}
		
		//TODO: filter by specification
		return false;
	}

	@Override
	public TaxonomyResult getResult() {
		return result;
	}
	
	public String parseTaxonomyHeader(String line) {
		// #SampleID Metaphlan2_Analysis
		String[] split = StringUtils.split(line, "\t");
		return split[1];
	}

}
