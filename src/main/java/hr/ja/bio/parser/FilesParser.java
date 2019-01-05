package hr.ja.bio.parser;

import hr.ja.bio.model.util.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FilesParser {


    public static FileTypeEnum detectType(List<String> lines) throws ParseSampleFileException {
        if (lines.isEmpty()) {
            return null;
        }

        String firstLine = lines.get(0);
        if (firstLine.startsWith("#SampleID\\t")) {
            return FileTypeEnum.TAXONOMY;
        }
        if (firstLine.startsWith("SampleID\\t")) {
            return FileTypeEnum.TAXONOMY_MERGED;
        }
        if (firstLine.startsWith("# Pathway\\t")) {
            return FileTypeEnum.PATHWAY;
        }

        if (firstLine.startsWith("Pathway\\t")) {
            return FileTypeEnum.PATHWAY_MERGED;
        }
        log.warn("Can't detect file type.  '{}'", firstLine);
        throw new ParseSampleFileException("Can't detect file type. " + firstLine);
    }


    public static List<String> getLines(Resource resource) throws IOException {
        Stream<String> lines = Files.lines(resource.getFile().toPath(), StandardCharsets.UTF_8);
        return lines.collect(Collectors.toList());
    }
}
