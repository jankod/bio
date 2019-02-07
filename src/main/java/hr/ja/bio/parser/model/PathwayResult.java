package hr.ja.bio.parser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathwayResult {
	String sampleName;
	List<PathAbundance> pathLineageAbundances = new ArrayList<>();

	public void addTaxonAbundance(PathAbundance lineageAbundance) {
		pathLineageAbundances.add(lineageAbundance);
	}

	public PathwayResult(String name) {
		sampleName = name;
	}
}
