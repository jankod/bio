package hr.ja.bio.model.util;

public enum TaxRankEnum {

    KINGDOM('k', 1),
    PHYLUM('p', 2),
    CLASS('c', 3),
    ORDER('o', 4),
    FAMILY('f', 5),
    GENUS('g', 6),
    SPECIES('s', 7),
    STRAIN('t', 8);

    private char charName;
    private int order;

    TaxRankEnum(char charName, int order) {
        this.charName = charName;
        this.order = order;
    }

    public char getCharName() {
        return charName;
    }

    public int getOrder() {
        return order;
    }
}
