package hr.ja.bio.parser;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.Lineage;
import hr.ja.bio.parser.model.SampleFileType;
import hr.ja.bio.parser.model.TaxRankType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserUtil {

	public static <R> R parse(IFileParser<R> parser, String content) throws ParseSampleFileException {
		List<String> lines = toLines(content);
		String line = null;
		int lineNum = 0;
		try {
			for (Iterator<String> it = lines.iterator(); it.hasNext();) {
				line = it.next();
				if (!parser.filterLine(line, lineNum)) {
					parser.parseLine(line, lineNum);
				}
				lineNum++;
			}
		} catch (Exception e) {
			log.error("{} line:'{}' num:'{}'", e.getMessage(), line, lineNum, e);
			throw new ParseSampleFileException(e.getMessage(), e, line, lineNum);
		}
		return parser.getResult();
	}

	public static ILineage parseLineage(String linePart, String separator) {
		Lineage lineage = new Lineage();
		// k__Bacteria|p__Firmicutes|c__Clostridia|o__Clostridiales|f__Ruminococcaceae|g__Ruminococcaceae_noname
		String[] split = StringUtils.splitByWholeSeparator(linePart, separator + "");
		for (String rank : split) {
			TaxRankType[] values = TaxRankType.values();
			for (TaxRankType r : values) {
				if (rank.startsWith(r.getCharName() + "__")) {
					r.populateRank(lineage, rank.substring(3).trim());
				}
			}
		}
		return lineage;
	}

	public static SampleFileType detectType(String content) throws ParseSampleFileException {
		if (StringUtils.isBlank(content)) {
			throw new ParseSampleFileException("Empty content");
		}
		LineNumberReader reader = new LineNumberReader(new StringReader(content));

		String firstLine;
		try {
			firstLine = reader.readLine().trim();

			if (firstLine.startsWith("#SampleID\t")) {
				return SampleFileType.TAXONOMY;
			}
			if (firstLine.startsWith("SampleID\t")) {
				return SampleFileType.TAXONOMY_MERGED;
			}
			if (firstLine.startsWith("# Pathway\t")) {
				return SampleFileType.PATHWAY;
			}

			if (firstLine.startsWith("Pathway\t")) {
				return SampleFileType.PATHWAY_MERGED;
			}
		} catch (Exception e) {
			log.error("", e);
			throw new ParseSampleFileException(e.getMessage(), "");
		}
		log.warn("Can't detect file type.  '{}'", firstLine);
		throw new ParseSampleFileException("Can't detect file type. '" + firstLine + "'", "");
	}

	public List<String> readFileToLines(File file) throws IOException {
		try (Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
			return lines.collect(Collectors.toList());
		}
	}

	public static List<String> toLines(String content) {
		if (content == null) {
			return Collections.emptyList();
		}
		List<String> lines = new ArrayList<>();
		Scanner s = new Scanner(content);
		while (s.hasNextLine()) {
			String line = s.nextLine();
			lines.add(line);
		}
		s.close();
		return lines;
	}

}
