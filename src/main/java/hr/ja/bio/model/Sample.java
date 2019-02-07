package hr.ja.bio.model;

import hr.ja.bio.repository.MyAbstractPersistable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Getter
@Setter
@Table()
public class Sample extends MyAbstractPersistable<Long> implements Serializable {

    @Column
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    Project project;

//    @OneToMany(mappedBy = "sample", orphanRemoval = true)
//    Set<SampleFile> sampleFiles;

}
