package hr.ja.bio.parser.old;

import hr.ja.bio.parser.model.LineageAbundance;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaxonomyAbundanceResult {

    String sampleName;
    List<LineageAbundance> taxonomyAbundances;

    public TaxonomyAbundanceResult() {
    }



    public TaxonomyAbundanceResult(String sampleName) {
        this.sampleName = sampleName;
    }


    public void addTaxonAbundance(LineageAbundance tax) {
        if (taxonomyAbundances == null) {
            taxonomyAbundances = new ArrayList<>();
        }
        taxonomyAbundances.add(tax);
    }
}
