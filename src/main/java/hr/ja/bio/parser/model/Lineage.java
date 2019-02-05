package hr.ja.bio.parser.model;

import lombok.Data;

@Data
public class Lineage implements ILineage {
    String rank1Kingdom;

    String rank2Phylum;

    String rank3Class;

    String rank4Order;

    String rank5Family;

    String rank6Genus;

    String rank7Species;

    String rank8Strain;
}
