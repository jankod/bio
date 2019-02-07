package hr.ja.bio.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import hr.ja.bio.parser.model.PathAbundance;
import hr.ja.bio.parser.model.PathwayResult;

public class PathwayParserTest {
	
	@Test
	public void testParsePathway() throws Exception {
		String taxonomyMerged = ParserTestUtil.filesDirPath + "\\example1_pathabundance.tsv";

		PathwayParser parser = new PathwayParser();
		String content = FileUtils.readFileToString(new File(taxonomyMerged), StandardCharsets.UTF_8);

		PathwayResult result = ParserUtil.parse(parser, content);

		assertEquals("QFS30_kneaddata_merged_Abundance", result.getSampleName());

		List<PathAbundance> pathAbundances = result.getPathLineageAbundances();
		for (PathAbundance pathAbundance : pathAbundances) {
			//log.debug("path abundance {}", pathAbundance);
		}
		assertNotNull(pathAbundances);
		// assertEquals(1395 -1147, pathAbundances.size());

		{
			PathAbundance p1Abundance = ParserTestUtil.findPath("PWY-5173: superpathway of acetyl-CoA biosynthesis", pathAbundances);
			assertNotNull(p1Abundance);
			assertEquals(1.1617954619, p1Abundance.getAbundance(), 0);
		}
		{
			// Bacteroides_eggerthii
			PathAbundance p1Abundance = ParserTestUtil.findPath("Bacteroides_eggerthii", pathAbundances);
			assertNull(p1Abundance);
		}

	}


}
