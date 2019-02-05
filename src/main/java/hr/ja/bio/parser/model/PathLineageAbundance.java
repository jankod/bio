package hr.ja.bio.parser.model;

import hr.ja.bio.parser.model.Lineage;
import lombok.Data;

@Data
public class PathLineageAbundance {
    Double abundance;
    String path;
    ILineage lineage;
}
