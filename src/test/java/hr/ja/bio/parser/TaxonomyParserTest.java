package hr.ja.bio.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import hr.ja.bio.parser.ParseSampleFileException;
import hr.ja.bio.parser.ParserUtil;
import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.TaxonomyResult;

public class TaxonomyParserTest {

	@Test
	public void testParseLinage() throws Exception {
		{
			ILineage result = ParserUtil.parseLineage("k__Bacteria|p__Proteobacteria", "|");
			assertEquals("Bacteria", result.getRank1Kingdom());
			assertEquals("Proteobacteria", result.getRank2Phylum());
		}
		{
			ILineage result = ParserUtil.parseLineage(
					"k__Bacteria|p__Firmicutes|c__Clostridia|o__Clostridiales|f__Ruminococcaceae|g__Ruminococcaceae_noname|s__Ruminococcaceae_bacterium_D16	",
					"|");

			assertEquals("Bacteria", result.getRank1Kingdom());
			assertEquals("Firmicutes", result.getRank2Phylum());
			assertEquals("Clostridia", result.getRank3Class());
			assertEquals("Clostridiales", result.getRank4Order());
			assertEquals("Ruminococcaceae", result.getRank5Family());
			assertEquals("Ruminococcaceae_noname", result.getRank6Genus());
			assertEquals("Ruminococcaceae_bacterium_D16", result.getRank7Species());

		}
	}

	@Test
	public void testTaxonomy() throws IOException, ParseSampleFileException {

		String file1 = ParserTestUtil.filesDirPath + "\\example1_metaphlan.txt";
		TaxonomyParser parser = new TaxonomyParser();

		TaxonomyResult result = ParserUtil.parse(parser,
				FileUtils.readFileToString(new File(file1), StandardCharsets.UTF_8));
		assertNotNull(result);
		assertEquals(199, result.getLineageAbundances().size());
		assertEquals("Metaphlan2_Analysis", result.getSampleName());

	}
}
