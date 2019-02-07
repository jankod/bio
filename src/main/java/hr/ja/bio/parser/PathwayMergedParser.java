package hr.ja.bio.parser;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.PathAbundance;
import hr.ja.bio.parser.model.PathwayResult;
import hr.ja.bio.parser.model.TaxonomyResult;

public class PathwayMergedParser implements IFileParser<List<PathwayResult>> {
	List<PathwayResult> results = new ArrayList<PathwayResult>();

	@Override
	public void parseLine(String line, int lineNumber) {
		if (lineNumber == 0) {
			List<String> samplesName = parsePathwayMergedHeader(line);
			for (String name : samplesName) {
				results.add(new PathwayResult(name));
			}
			return;
		}
		
		String[] split = StringUtils.split(line, "\t");
		String lineageAndPath = split[0];

		String[] splitPathAndLineage = StringUtils.splitByWholeSeparator(lineageAndPath, "|");
		String pathName = splitPathAndLineage[0].trim();

		for (int i = 1; i < split.length; i++) {
			double abundance = Double.parseDouble(split[i]);
			results.get(i-1).addTaxonAbundance(new PathAbundance(abundance, pathName));
		}
		
	}

	private List<String> parsePathwayMergedHeader(String line) {
		List<String> result = new ArrayList<String>();
		// Pathway CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6 CSM5MCWC
		String[] split = StringUtils.split(line, "\t");
		for (int i = 1; i < split.length; i++) {
			result.add(split[i].trim());
		}
		return result;
	}

	@Override
	public boolean filterLine(String line, int lineNumber) {
		if (lineNumber == 0) {
			return false;
		}
		if (isEmpty(line) || line.startsWith("#")) {
			return true;
		}
		
		
		return false;
	}

	@Override
	public List<PathwayResult> getResult() {
		return results;
	}

}
