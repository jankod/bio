package hr.ja.bio.model;

public enum TaxRank {

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

    TaxRank(char charName, int order) {
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
