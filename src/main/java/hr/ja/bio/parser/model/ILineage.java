package hr.ja.bio.parser.model;

public interface ILineage {
    String getRank1Kingdom();

    String getRank2Phylum();

    String getRank3Class();

    String getRank4Order();

    String getRank5Family();

    String getRank6Genus();

    String getRank7Species();

    void setRank1Kingdom(String rank1Kingdom);

    void setRank2Phylum(String rank2Phylum);

    void setRank3Class(String rank3Class);

    void setRank4Order(String rank4Order);

    void setRank5Family(String rank5Family);

    void setRank6Genus(String rank6Genus);

    void setRank7Species(String rank7Species);

    void setRank8Strain(String rank8Strain);
}
