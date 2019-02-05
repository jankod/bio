package hr.ja.bio.parser;

import hr.ja.bio.parser.model.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@UtilityClass
@Slf4j
public class SampleFileParserUtil {

	public TaxonomyResult parseTaxonomy(String content) throws IOException, ParseSampleFileException {
//        #SampleID	Metaphlan2_Analysis
//        k__Bacteria	100.0
//        k__Bacteria|p__Bacteroidetes	61.53827
//        k__Bacteria|p__Proteobacteria	20.86671

		TaxonomyResult result = new TaxonomyResult();

		List<String> lines = toLines(content);
		boolean first = true;
		for (String line : lines) {
			if (first) {
				String sampleName = parseTaxonomyHeader(line);
				result.setSampleName(sampleName);
				first = false;
				continue;
			}

			if (isEmpty(line) || line.startsWith("#")) {
				continue;
			}

			String[] split = StringUtils.split(line, "\t");
			throwIf(split.length == 2, "Split fail", line);

			ILineage lineage = parseLineage(split[0], "|");
			Double abundance = Double.parseDouble(split[1]);
			result.addTaxonAbundance(new LineageAbundance(abundance, lineage));
		}
		return result;
	}

	private void throwIf(boolean check, String msg, String line) throws ParseSampleFileException {
		if (!check) {
			throw new ParseSampleFileException(msg, line);
		}
	}

	public String parseTaxonomyHeader(String line) throws ParseSampleFileException {
		// #SampleID Metaphlan2_Analysis
		String[] split = StringUtils.split(line, "\t");
		throwIf(split.length == 2, "Split error", line);
		return split[1];
	}

	public List<TaxonomyResult> parseTaxonomyMereged(String content) throws ParseSampleFileException {
		// SampleID CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6
		// k__Archaea|p__Euryarchaeota|c__Methanobacteria 0 0 0 0 0 0
		// k__Bacteria 0.999938 0.998982 0.985808 0.999924 1 1
		// unclassified 0 0 0 0 0 0
		List<TaxonomyResult> result = new ArrayList<>();

		List<String> lines = toLines(content);
		boolean first = true;
		for (String line : lines) {
			if (first) {
				List<String> samplesName = parseTaxonomyMeregedHeader(line);
				for (String name : samplesName) {
					result.add(new TaxonomyResult(name)); 
				}
				first = false;
				continue;
			}

			if (isEmpty(line) || line.startsWith("#")) {
				continue;
			}

			String[] split = StringUtils.split(line, "\t");
			String linagePart = split[0];
			for (int i = 1; i < split.length; i++) {
				double abundance = Double.parseDouble(split[i]);
				if(abundance != 0) {
					ILineage lineage = parseLineage(linagePart, "|");
					result.get(i).addTaxonAbundance(new LineageAbundance(abundance, lineage));	
				}
			}

		}
		return result;
	}

	public List<String> parseTaxonomyMeregedHeader(String line) {
		// SampleID CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6
		String[] split = StringUtils.splitByWholeSeparator(line, "\t");
		ArrayList<String> result = new ArrayList<String>(split.length - 1);
		for (int i = 1; i < split.length; i++) {
			result.add(split[i].trim());
		}
		return result;
	}

	public PathwayResult parsePathway(String content) {
//        # Pathway	QFS30_kneaddata_merged_Abundance
//        UNMAPPED	1854175.4032142079
//        UNINTEGRATED	3254358.9584659408
//        UNINTEGRATED|g__Bacteroides.s__Bacteroides_uniformis	801569.0092330702
//        PWY-7219: adenosine ribonucleotides de novo biosynthesis|g__Parabacteroides.s__Parabacteroides_johnsonii	3.6364496164
//        PWY-6590: superpathway of Clostridium acetobutylicum acidogenic fermentation	116.2876714964
//        PWY-6606: guanosine nucleotides degradation II	114.1106672858

		PathwayResult result = new PathwayResult();
		return result;
	}

	public List<PathwayResult> parsePathwayMerged(String content) {
		// Pathway CSM5FZ4M CSM5MCUO CSM5MCVL CSM5MCVN CSM5MCW6 CSM5MCWC
		// 1CMET2-PWY: N10-formyl-tetrahydrofolate biosynthesis 0.0158099 0.0101701
		// 0.0167429
		// ASPASN-PWY: superpathway of L-aspartate and L-asparagine
		// biosynthesis|g__Bacteroides.s__Bacteroides_fluxus 0 0 0 0
		// VALSYN-PWY: L-valine biosynthesis|unclassified 0.0014926 0.0048921
		// 0.000614847 0.00204131 0.00330515

		List<PathwayResult> result = new ArrayList<>();
		return result;
	}

	public ILineage parseLineage(String linePart, String separator) {
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

	public SampleFileType detectType(String content) throws ParseSampleFileException {
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
		} catch (IOException e) {
			log.error("", e);
			throw new ParseSampleFileException(e.getMessage(), "", reader.getLineNumber());
		}
		log.warn("Can't detect file type.  '{}'", firstLine);
		throw new ParseSampleFileException("Can't detect file type. '" + firstLine + "'", "");
	}

	public List<String> readFileToLines(File file) throws IOException {
		try (Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
			return lines.collect(Collectors.toList());
		}
	}

	public List<String> toLines(String content) {
		if (content == null) {
			return Collections.emptyList();
		}
		List<String> lines = new ArrayList<>();
		Scanner s = new Scanner(content);
		while (s.hasNextLine()) {
			String line = s.nextLine();
			lines.add(line);
		}
		return lines;
	}

	public static void main(String[] args) throws IOException, ParseSampleFileException {
		String path = "C:\\Data\\PBF\\Projekti\\2018-UMCGMicrobiomeWeb\\example_data\\example_pathways_humann2_merged.tsv";

		FileReader in = new FileReader(path);
		log.debug(detectType(IOUtils.toString(in)).toString());
		in.close();

	}
}
