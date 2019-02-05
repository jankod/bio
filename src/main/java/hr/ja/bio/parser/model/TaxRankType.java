package hr.ja.bio.parser.model;

import hr.ja.bio.parser.model.ILineage;

import java.util.function.Consumer;
import java.util.function.Function;

public enum TaxRankType {

    KINGDOM('k', 1) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank1Kingdom(rank);
        }
    },
    PHYLUM('p', 2) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank2Phylum(rank);
        }
    },
    CLASS('c', 3) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank3Class(rank);
        }
    },
    ORDER('o', 4) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank4Order(rank);
        }
    },
    FAMILY('f', 5) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank5Family(rank);
        }
    },
    GENUS('g', 6) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank6Genus(rank);
        }
    },
    SPECIES('s', 7) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank7Species(rank);
        }
    },
    STRAIN('t', 8) {
        @Override
        public void populateRank(ILineage lineage, String rank) {
            lineage.setRank8Strain(rank);
        }
    };

    private char charName;
    private int order;

    public abstract void populateRank(ILineage lineage, String rank);

    TaxRankType(char charName, int order) {
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
