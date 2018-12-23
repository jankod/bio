package hr.ja.bio.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@ToString
public class Sample extends AbstractPersistable<Long> {

    @Column
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    Project project;

    @OneToMany(mappedBy = "sample", orphanRemoval = true)
    Set<SampleFile> sampleFiles;

}
