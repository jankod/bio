package hr.ja.bio.model;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
//@Builder
public class TaxonomyAbundance extends AbstractPersistable<Long> {

    @Column
    Double abundance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    SampleFile file;

    @Column(name = "rank1_kingdom")
    String kingdom;
    @Column(name = "rank2_phylum")
    String phylum;

    @Column(name = "rank3_class")
    String rank3_class;

    @Column(name = "rank4_order")
    String rank4_order;


    @Column(name = "rank5_family")
    String rank5_family;

    @Column(name = "rank6_genus")
    String rank6_genus;

    @Column(name = "rank6_species")
    String rank6_species;

    @Column(name = "rank7_strain")
    String rank7_strain;


}
