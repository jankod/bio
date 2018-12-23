package hr.ja.bio.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
public class SampleFile extends AbstractPersistable<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Sample sample;

    @Column
    String type;

    @Column
    String fileName;

    @Column
    String sampleName;

}