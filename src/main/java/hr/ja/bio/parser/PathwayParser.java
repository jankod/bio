package hr.ja.bio.parser;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.PathAbundance;
import hr.ja.bio.parser.model.PathwayResult;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class PathwayParser implements IFileParser<PathwayResult> {

	PathwayResult result = new PathwayResult();

	public void parseLine(String line, int lineNumber) {
		if (lineNumber == 0) {
			String sampleId = parsePathwayHeader(line);
			result.setSampleName(sampleId);
		} else {

			if (StringUtils.isEmpty(line) || line.startsWith("#")) {
				return;
			}

			String[] split = StringUtils.split(line, "\t");

			String path = split[0];

			Double abundance = Double.parseDouble(split[1]);
			result.addTaxonAbundance(new PathAbundance(abundance, path));

		}

	}

	private String parsePathwayHeader(String line) {
		// Use last split word
		String[] split = StringUtils.splitByWholeSeparator(line, "\t");
		return split[split.length - 1];
	}

	public boolean filterLine(String line, int lineNumber) {
		if(lineNumber == 0) {
			return false;
		}
		
		if (line.startsWith("UNMAPPED")) {
			return true;
		}
		if (line.startsWith("UNINTEGRATED")) {
			return true;
		}

		String[] split = StringUtils.split(line, "\t");
		if (split[0].contains("|")) {
			return true;
		}
		return false;
	}

	@Override
	public PathwayResult getResult() {
		return result;
	}
}
