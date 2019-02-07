package hr.ja.bio.model;

import hr.ja.bio.parser.model.ILineage;
import hr.ja.bio.repository.MyAbstractPersistable;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
//@Builder
@Table()
public class TaxonomyAbundance extends MyAbstractPersistable<Long> implements Serializable {

    @Column
    Double abundance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id")
    Sample sample;

    @Column(name = "rank1_kingdom")
    Integer rank1Kingdom;

    @Column(name = "rank2_phylum")
    Integer rank2Phylum;

    @Column(name = "rank3_class")
    Integer rank3Class;

    @Column(name = "rank4_order")
    Integer rank4Order;

    @Column(name = "rank5_family")
    Integer rank5Family;

    @Column(name = "rank6_genus")
    Integer rank6Genus;

    @Column(name = "rank7_species")
    Integer rank7Species;

    @Column(name = "rank8_strain")
    Integer rank8Strain;

}
