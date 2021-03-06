package hr.ja.bio.model;

import hr.ja.bio.repository.MyAbstractPersistable;
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
@Table
public class PathwayAbundance extends MyAbstractPersistable<Long> implements Serializable {

    @Column
    String pathway;

    @Column
    Double abundance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_id")
    Sample file;

}
