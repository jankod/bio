package hr.ja.bio.model;

public enum TaxRank {

    KINGDOM('k'),
    PHYLUM('p'),
    CLASS('c'),
    ORDER('o'),
    FAMILY('f'),
    GENUS('g'),
    SPECIES('s'),
    STRAIN('t');

    private char charName;

    TaxRank(char charName) {
        this.charName = charName;
    }

    public char getCharName() {
        return charName;
    }
}
