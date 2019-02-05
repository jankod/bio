package hr.ja.bio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
public class SampleFile extends AbstractPersistable<Long> implements Serializable {

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "sample_id")
//    Sample sample;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    Project project;

    @Column
    String type;

    @Column
    String fileName;

}