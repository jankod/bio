package hr.ja.bio.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import hr.ja.bio.parser.ParserUtil;
import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.TaxonomyResult;

public class TaxonomyMergedParserTest {

	@Test
	public void testParseTaxonomyMereged() throws Exception {
		String taxonomyMerged = ParserTestUtil.filesDirPath + "\\example_metaphlan_merged.tsv";
		TaxonomyMergedParser parser = new TaxonomyMergedParser();
		List<TaxonomyResult> result = ParserUtil.parse(parser, FileUtils.readFileToString(new File(taxonomyMerged), StandardCharsets.UTF_8));

		assertEquals(1638, result.size());
		{
			TaxonomyResult resultCSM5FZ4M = ParserTestUtil.findSample(result, "CSM5FZ4M");
			assertNotNull(resultCSM5FZ4M);
			{
				LineageAbundance lineageAbundance = ParserTestUtil.findSpeciesLinageAbundance(resultCSM5FZ4M,
						"Bacteroides_xylanisolvens");
				assertNotNull(lineageAbundance);
				assertEquals(0.011119D, lineageAbundance.getAbundance().doubleValue(), 0);
			}
			{
				LineageAbundance lineageAbundance = ParserTestUtil.findSpeciesLinageAbundance(resultCSM5FZ4M,
						"Clostridium_hathewayi");
				assertNull(lineageAbundance);
			}

			{
				LineageAbundance lineageAbundance = ParserTestUtil.findSpeciesLinageAbundance(resultCSM5FZ4M,
						"Mitsuokella_unclassified");
				assertEquals(0.0001278D, lineageAbundance.getAbundance().doubleValue(), 0);

			}
		}

		{
			TaxonomyResult tax = ParserTestUtil.findSample(result, "HSM5FZC2_P");

			// s__Bacteroides_dorei|t__Bacteroides_dorei_unclassified 0.0556869

			LineageAbundance lineageAbundance = ParserTestUtil.findSpeciesLinageAbundance(tax, "Bacteroides_dorei");
			assertEquals(0.0556869D, lineageAbundance.getAbundance().doubleValue(), 0);

			LineageAbundance lineageAbundanceStrain = ParserTestUtil.findStrainLinageAbundance(tax,
					"Bacteroides_dorei_unclassified");
			assertEquals(0.0556869D, lineageAbundanceStrain.getAbundance().doubleValue(), 0);

		}
	}

	@Test
	public void testParseTaxonomyMeregedHeader() throws Exception {
		String line = "SampleID\tCSM5FZ4M\tCSM5MCUO\tCSM5MCVL\tCSM5MCVN\tCSM5MCW6 ";
		List<String> result = new TaxonomyMergedParser().parseTaxonomyMeregedHeader(line);
		assertEquals(5, result.size());

		assertEquals("CSM5FZ4M", result.get(0));

		assertEquals("CSM5MCUO", result.get(1));

		assertEquals("CSM5MCVL", result.get(2));

		assertEquals("CSM5MCVN", result.get(3));

		assertEquals("CSM5MCW6", result.get(4));
	}

}
