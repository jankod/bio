package hr.ja.bio.parser.model;

import hr.ja.bio.parser.model.Lineage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathAbundance {
    Double abundance;
    String path;
}
