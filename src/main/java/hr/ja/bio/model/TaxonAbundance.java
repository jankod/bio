package hr.ja.bio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
//@Builder
public class TaxonAbundance extends AbstractPersistable<Long> {

    @Column
    Double abundance;

    @ManyToOne
    @JoinColumn(name = "taxonomy_file_id")
    TaxonomyFile file;

    @Column
    String kingdom;
    @Column
    String phylum;

    @Column(name = "class")
    String class_rank;

    @Column(name = "order2")
    String order;
    @Column
    String family;
    @Column
    String genus;
    @Column
    String species;
    @Column
    String strain;


}