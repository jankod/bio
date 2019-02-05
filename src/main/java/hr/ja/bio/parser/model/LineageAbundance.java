package hr.ja.bio.parser.model;

import hr.ja.bio.parser.model.Lineage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor()
public class LineageAbundance  {
    Double abundance;
    ILineage lineage;

}

