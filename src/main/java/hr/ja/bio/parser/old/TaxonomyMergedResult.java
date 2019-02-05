package hr.ja.bio.parser.old;


import hr.ja.bio.parser.model.TaxonomyResult;
import lombok.Data;

import java.util.List;

@Data
public class TaxonomyMergedResult {

    List<TaxonomyResult> taxonomyParseResults;
}
