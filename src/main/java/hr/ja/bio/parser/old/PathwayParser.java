package hr.ja.bio.parser.old;

import hr.ja.bio.model.PathwayAbundance;
import hr.ja.bio.parser.ParseSampleFileException;
import hr.ja.bio.parser.model.PathwayResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Deprecated
@Slf4j
public class PathwayParser {

	public PathwayResult parse(String content) throws ParseSampleFileException {
		String[] lines = StringUtils.split(content, "\n");
		PathwayResult result = new PathwayResult();
		for (String line : lines) {
			try {
				if (line.startsWith("# Pathway")) {
					String[] split = StringUtils.split(line, "\t");
					result.setSampleName(split[1].trim());
					continue;
				}
				if (line.startsWith("#")) {
					// comment ignore
					continue;
				}
				if (StringUtils.isEmpty(line)) {
					continue;
				}

				{
					String[] split = StringUtils.split(line, "\t");
					PathwayAbundance p = new PathwayAbundance();
					p.setAbundance(Double.parseDouble(split[1]));

					String[] s2 = StringUtils.split(split[0], "|");

					p.setPathway(s2[0]);
					if (s2.length > 1)
						parseTaxonomyPart(s2[1], p);

				}
			} catch (Throwable e) {
				log.error("Parse error with line '{}'", line, e);
				throw new ParseSampleFileException(e.getMessage(), line);
			}
		}

		return result;
	}

	private void parseTaxonomyPart(String taxLine, PathwayAbundance p) {
		// g__Parabacteroides.s__Parabacteroides_distasonis
	}

}
