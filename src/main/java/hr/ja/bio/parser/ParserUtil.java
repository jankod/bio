package hr.ja.bio.parser;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.File;
import java.io.FileReader;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import com.opencsv.CSVReader;

import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.parser.model.Lineage;
import hr.ja.bio.parser.model.LineageAbundance;
import hr.ja.bio.parser.model.PathAbundance;
import hr.ja.bio.parser.model.PathwayResult;
import hr.ja.bio.parser.model.SampleFileType;
import hr.ja.bio.parser.model.TaxRankType;
import hr.ja.bio.parser.model.TaxonomyResult;
import lombok.experimental.UtilityClass;
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

//	public PathwayResult parsePathway(String content) throws ParseSampleFileException {
////      # Pathway	QFS30_kneaddata_merged_Abundance
////      UNMAPPED	1854175.4032142079
////      UNINTEGRATED	3254358.9584659408
////      UNINTEGRATED|g__Bacteroides.s__Bacteroides_uniformis	801569.0092330702
////      PWY-7219: adenosine ribonucleotides de novo biosynthesis|g__Parabacteroides.s__Parabacteroides_johnsonii	3.6364496164
////      PWY-6590: superpathway of Clostridium acetobutylicum acidogenic fermentation	116.2876714964
////      PWY-6606: guanosine nucleotides degradation II	114.1106672858
//
//		PathwayResult result = new PathwayResult();
//		List<String> lines = toLines(content);
//		boolean first = true;
//		try {
//			for (String line : lines) {
//				if (first) {
//					String sampleName = parsePathwayHeader(line);
//					result.setSampleName(sampleName);
//					first = false;
//					continue;
//				}
//
//				if (isEmpty(line) || line.startsWith("#")) {
//					continue;
//				}
//
////				if (filter != null && filter.filterLine(line)) {
////					continue;
////				}
//
//				String[] split = StringUtils.split(line, "\t");
//
//				String lineageAndPath = split[0];
//
//				String[] splitPathAndLineage = StringUtils.splitByWholeSeparator(lineageAndPath, "|");
//
//				String pathName = splitPathAndLineage[0].trim();
//
//				ILineage lineage = parseLineage(splitPathAndLineage[1], ".");
//
//				Double abundance = Double.parseDouble(split[1]);
//				result.addTaxonAbundance(new PathAbundance(abundance, pathName));
//			}
//		} catch (Exception e) {
//			throw new ParseSampleFileException(e);
//		}
//
//		return result;
//	}
//
//	private String parsePathwayHeader(String line) {
//		// Use last split word
//		String[] split = StringUtils.splitByWholeSeparator(line, "\t");
//		return split[split.length - 1];
//	}

//	public TaxonomyResult parseTaxonomy(String content) throws IOException, ParseSampleFileException {
////        #SampleID	Metaphlan2_Analysis
////        k__Bacteria	100.0
////        k__Bacteria|p__Bacteroidetes	61.53827
////        k__Bacteria|p__Proteobacteria	20.86671
//
//		TaxonomyResult result = new TaxonomyResult();
//
//		List<String> lines = toLines(content);
//		boolean first = true;
//		for (String line : lines) {
//			if (first) {
//				String sampleName = parseTaxonomyHeader(line);
//				result.setSampleName(sampleName);
//				first = false;
//				continue;
//			}
//
//			if (isEmpty(line) || line.startsWith("#")) {
//				continue;
//			}
//
//			String[] split = StringUtils.split(line, "\t");
//			throwIf(split.length == 2, "Split fail", line);
//
//			ILineage lineage = parseLineage(split[0], "|");
//			Double abundance = Double.parseDouble(split[1]);
//			result.addTaxonAbundance(new LineageAbundance(abundance, lineage));
//		}
//		return result;
//	}

	public void throwIf(boolean check, String msg, String line) throws ParseSampleFileException {
		if (!check) {
			throw new ParseSampleFileException(msg, line);
		}
	}

//	

//	public List<TaxonomyResult> parseTaxonomyMereged(String content) throws ParseSampleFileException {
//		// SampleID CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6
//		// k__Archaea|p__Euryarchaeota|c__Methanobacteria 0 0 0 0 0 0
//		// k__Bacteria 0.999938 0.998982 0.985808 0.999924 1 1
//		// unclassified 0 0 0 0 0 0
//		List<TaxonomyResult> result = new ArrayList<>();
//
//		List<String> lines = toLines(content);
//		boolean first = true;
//		for (String line : lines) {
//			if (first) {
//				List<String> samplesName = parseTaxonomyMeregedHeader(line);
//				for (String name : samplesName) {
//					result.add(new TaxonomyResult(name));
//				}
//				first = false;
//				continue;
//			}
//
//			if (isEmpty(line) || line.startsWith("#")) {
//				continue;
//			}
//
//			String[] split = StringUtils.split(line, "\t");
//			String linagePart = split[0];
//			for (int i = 1; i < split.length; i++) {
//				double abundance = Double.parseDouble(split[i]);
//				if (abundance != 0) {
//					ILineage lineage = parseLineage(linagePart, "|");
//					result.get(i - 1).addTaxonAbundance(new LineageAbundance(abundance, lineage));
//				}
//			}
//		}
//		return result;
//	}

//	public List<String> parseTaxonomyMeregedHeader(String line) {
//		// SampleID CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6
//		String[] split = StringUtils.splitByWholeSeparator(line, "\t");
//		ArrayList<String> result = new ArrayList<String>(split.length - 1);
//		for (int i = 1; i < split.length; i++) {
//			result.add(split[i].trim());
//		}
//		return result;
//	}

//	public List<PathwayResult> parsePathwayMerged(String content) throws ParseSampleFileException {
////		Pathway	CSM5FZ4M	CSM5MCUO	CSM5MCVL	CSM5MCVN	CSM5MCW6	CSM5MCWC	CSM5MCWE	CSM5MCWG	CSM5M
////		1CMET2-PWY: N10-formyl-tetrahydrofolate biosynthesis	0.0158099	0.0101701	0.0167429	0.0180019	0.015
////		1CMET2-PWY: N10-formyl-tetrahydrofolate biosynthesis|g__Akkermansia.s__Akkermansia_muciniphila	0	0	0	0
////		1CMET2-PWY: N10-formyl-tetrahydrofolate biosynthesis|g__Bacteroides.s__Bacteroides_barnesiae	0	0	0	0
////		BIOTIN-BIOSYNTHESIS-PWY: biotin biosynthesis I|g__Shigella.s__Shigella_flexneri	0	0	0	0	0	0	0	0	0	0	0	0	0	0
////		BIOTIN-BIOSYNTHESIS-PWY: biotin biosynthesis I|g__Shigella.s__Shigella_sonnei	0	0	0	0	0	0	0	0	0	0	0	0	0	0
////		BIOTIN-BIOSYNTHESIS-PWY: biotin biosynthesis I|unclassified	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
////		BRANCHED-CHAIN-AA-SYN-PWY: superpathway of branched amino acid biosynthesis	0.00662973	0.01207	0.00132286	0.000188206	0.00713851	0
////		BRANCHED-CHAIN-AA-SYN-PWY: superpathway of branched amino acid biosynthesis|g__Akkermansia.s__Akkermansia_muciniphila	0	0	0	0
//
//		List<PathwayResult> results = new ArrayList<PathwayResult>();
//		List<String> lines = toLines(content);
//		boolean first = true;
//		for (String line : lines) {
//			if (first) {
//				List<String> samplesName = parsePathwayMergedHeader(line);
//				for (String name : samplesName) {
//					results.add(new PathwayResult(name));
//				}
//				first = false;
//				continue;
//			}
//
//			if (isEmpty(line) || line.startsWith("#")) {
//				continue;
//			}
//
//			String[] split = StringUtils.split(line, "\t");
//			throwIf(split.length == 2, "Split fail", line);
//			String lineageAndPath = split[0];
//
//			String[] splitPathAndLineage = StringUtils.splitByWholeSeparator(lineageAndPath, "|");
//			throwIf(splitPathAndLineage.length == 2, "Split fail", line);
//			String pathName = splitPathAndLineage[0].trim();
//			//ILineage lineage = parseLineage(splitPathAndLineage[1], ".");
//
//			for (int i = 1; i < split.length; i++) {
//				double abundance = Double.parseDouble(split[i]);
//				results.get(i).addTaxonAbundance(new PathAbundance(abundance, pathName));
//			}
//		}
//		return results;
//
//	}

//	private List<String> parsePathwayMergedHeader(String line) {
//		List<String> result = new ArrayList<String>();
//		// Pathway CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6 CSM5MCWC
//		String[] split = StringUtils.split(line, "\t");
//		for (int i = 1; i < split.length; i++) {
//			result.add(split[i].trim());
//		}
//		return result;
//	}

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
