package hr.ja.bio.model;

import hr.ja.bio.parser.model.SampleFileType;
import hr.ja.bio.repository.MyAbstractPersistable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
public class SampleFile extends MyAbstractPersistable<Long> implements Serializable {

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sample_id")
//    Sample sample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    Project project;

    @Column
    @Enumerated(EnumType.STRING)
    SampleFileType type;

    @Column
    String fileName;

    @Lob
    @Column()
    byte[] file;

}
