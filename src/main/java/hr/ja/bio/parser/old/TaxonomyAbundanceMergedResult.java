package hr.ja.bio.parser.old;


import lombok.Data;

import java.util.List;

@Data
public class TaxonomyAbundanceMergedResult {

    List<TaxonomyAbundanceResult> taxonomyParseResults;
}
