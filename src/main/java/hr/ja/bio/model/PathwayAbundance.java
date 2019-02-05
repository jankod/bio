package hr.ja.bio.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
//@Builder
@Table
public class PathwayAbundance extends AbstractPersistable<Long> implements Serializable {

    @Column
    String pathway;

    @Column
    Double abundance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id")
    Sample file;

    @Column(name = "rank1Kingdom")
    Integer rank1_kingdom;

    @Column(name = "rank2Phylum")
    Integer rank2_phylum;

    @Column(name = "rank3Class")
    Integer rank3_class;

    @Column(name = "rank4Order")
    Integer rank4_order;


    @Column(name = "rank5Family")
    Integer rank5_family;

    @Column(name = "rank6Genus")
    Integer rank6_genus;

    @Column(name = "rank7Species")
    Integer rank6_species;

    @Column(name = "rank8Strain")
    Integer rank7_strain;

}
