package hr.ja.bio;

import hr.ja.bio.parser.ParseSampleFileException;
import hr.ja.bio.parser.SampleFileParserUtil;
import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.TaxonomyResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ParserTest {

	String filesDirPath = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data";

 @Test
	public void testTaxonomy() throws IOException, ParseSampleFileException {

		String file1 = filesDirPath + "\\example1_metaphlan.txt";
		TaxonomyResult result = SampleFileParserUtil
				.parseTaxonomy(FileUtils.readFileToString(new File(file1), StandardCharsets.UTF_8));
		assertNotNull(result);
		assertEquals(199, result.getLineageAbundances().size());
		assertEquals("Metaphlan2_Analysis", result.getSampleName());

	}

	@Test
	public void testParseLinage() throws Exception {
		{
			ILineage result = SampleFileParserUtil.parseLineage("k__Bacteria|p__Proteobacteria", "|");
			assertEquals("Bacteria", result.getRank1Kingdom());
			assertEquals("Proteobacteria", result.getRank2Phylum());
		}
		{
			ILineage result = SampleFileParserUtil.parseLineage(
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
	public void testParseTaxonomyMeregedHeader() throws Exception {
		String line = "SampleID\tCSM5FZ4M\tCSM5MCUO\tCSM5MCVL\tCSM5MCVN\tCSM5MCW6 ";
		List<String> result = SampleFileParserUtil.parseTaxonomyMeregedHeader(line);
		assertEquals(5, result.size());
		
		assertEquals("CSM5FZ4M", result.get(0));
		
		assertEquals("CSM5MCUO", result.get(1));
		
		assertEquals("CSM5MCVL", result.get(2));
		
		assertEquals("CSM5MCVN", result.get(3));
		
		assertEquals("CSM5MCW6", result.get(4));
	}
}
