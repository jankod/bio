package hr.ja.bio.parser;

import hr.ja.bio.model.TaxonomyAbundance;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaxonomyAbundanceParseResult {

    String sampleName;
    List<TaxonomyAbundance> taxonomyAbundances;

    public void addTaxonAbundance(TaxonomyAbundance tax) {
        if(taxonomyAbundances == null) {
            taxonomyAbundances = new ArrayList<>();
        }
        taxonomyAbundances.add(tax);
    }
}
