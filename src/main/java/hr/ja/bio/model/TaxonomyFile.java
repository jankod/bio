package hr.ja.bio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
//@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxonomyFile extends AbstractPersistable<Long> {


    @Singular
    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL)
    List<TaxonAbundance> taxonomies;

    @Column
    String idName;

    @Column
    String fileName;

    public void addTaxonAbundance(TaxonAbundance tax) {
        if (taxonomies == null) {
            taxonomies = new ArrayList<>(220);
        }
        tax.setFile(this);
        taxonomies.add(tax);
    }
}