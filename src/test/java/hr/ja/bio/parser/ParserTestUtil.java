package hr.ja.bio.parser;

import java.util.List;

import org.junit.Test;

import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.PathAbundance;
import hr.ja.bio.parser.model.TaxonomyResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserTestUtil {

	static String filesDirPath = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data";

	public static LineageAbundance findSpeciesLinageAbundance(TaxonomyResult tax, String speciesName) {
		List<LineageAbundance> lineageAbundances = tax.getLineageAbundances();
		for (LineageAbundance lineageAbundance : lineageAbundances) {
			String species = lineageAbundance.getLineage().getRank7Species();
		//	log.debug("{}={}", speciesName, species);
			if (speciesName.equals(species)) {
				return lineageAbundance;
			}
		}
		return null;
	}

	public static LineageAbundance findStrainLinageAbundance(TaxonomyResult tax, String speciesName) {
		List<LineageAbundance> lineageAbundances = tax.getLineageAbundances();
		for (LineageAbundance lineageAbundance : lineageAbundances) {
			if (speciesName.equals(lineageAbundance.getLineage().getRank8Strain())) {
				return lineageAbundance;
			}
		}
		return null;
	}

	public static TaxonomyResult findSample(List<TaxonomyResult> result, String sampleName) {
		for (TaxonomyResult taxonomyResult : result) {
			if (taxonomyResult.getSampleName().equals(sampleName)) {
				return taxonomyResult;
			}
		}
		return null;
	}


	public static PathAbundance findPath(String path, List<PathAbundance> pathLineageAbundances) {
		for (PathAbundance pathLineageAbundance : pathLineageAbundances) {
			if (path.equals(pathLineageAbundance.getPath())) {
				return pathLineageAbundance;
			}
		}
		return null;
	}

	@Test
	public void testParseTaxonomyMerged() {

	}
}
