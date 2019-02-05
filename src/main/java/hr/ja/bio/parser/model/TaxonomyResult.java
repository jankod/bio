package hr.ja.bio.parser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TaxonomyResult {

    String sampleName;
    List<LineageAbundance> lineageAbundances = new ArrayList<>();

    public TaxonomyResult(String sampleName) {
        this.sampleName = sampleName;
    }

    public void addTaxonAbundance(LineageAbundance oneTax) {
        lineageAbundances.add(oneTax);
    }
}
