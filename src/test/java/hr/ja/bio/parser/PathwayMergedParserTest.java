package hr.ja.bio.parser;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import hr.ja.bio.parser.model.PathwayResult;

public class PathwayMergedParserTest {

	@Test
	public void testPathwayMerged() throws IOException, ParseSampleFileException {
		String filePath = ParserTestUtil.filesDirPath + "\\example_pathways_humann2_merged.tsv";
		PathwayMergedParser parser = new PathwayMergedParser();
		String content = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		List<PathwayResult> result = ParserUtil.parse(parser, content);
		assertNotNull(result);
		
	}
}
