package hr.ja.bio.parser;

import hr.ja.bio.parser.ParseSampleFileException;
import hr.ja.bio.parser.PathwayParser;
import hr.ja.bio.parser.ParserUtil;
import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.PathAbundance;
import hr.ja.bio.parser.model.PathwayResult;
import hr.ja.bio.parser.model.TaxonomyResult;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
	public void testParseTaxonomyMereged() {

	}
}
