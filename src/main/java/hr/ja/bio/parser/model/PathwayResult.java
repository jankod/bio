package hr.ja.bio.parser.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PathwayResult {
    String sampleName;
    List<PathLineageAbundance> pathLineageAbundances = new ArrayList<>();
}
