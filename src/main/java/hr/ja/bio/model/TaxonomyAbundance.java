package hr.ja.bio.model;

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
@Table(name = "taxonomy_abundance")
public class TaxonomyAbundance extends AbstractPersistable<Long> implements Serializable {

    @Column
    Double abundance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id")
    Sample sample;

    @Column(name = "rank1_kingdom")
    Integer rank1_kingdom;

    @Column(name = "rank2_phylum")
    Integer rank2_phylum;

    @Column(name = "rank3_class")
    Integer rank3_class;

    @Column(name = "rank4_order")
    Integer rank4_order;


    @Column(name = "rank5_family")
    Integer rank5_family;

    @Column(name = "rank6_genus")
    Integer rank6_genus;

    @Column(name = "rank7_species")
    Integer rank7_species;

    @Column(name = "rank8_strain")
    Integer rank8_strain;


}
