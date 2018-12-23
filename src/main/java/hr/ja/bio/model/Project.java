package hr.ja.bio.model;


import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Project extends AbstractPersistable<Long> implements Serializable {

    @Column
    String name;


    @OneToMany()
    @JoinColumn()
    Set<ProjectMember> members;

    @OneToMany()
    @JoinColumn
    Set<Sample> samples;

    public void addSample(Sample sample) {
        if(samples == null) {
            samples = new HashSet<>(3);
        }
        samples.add(sample);
        sample.setProject(this);
    }
}
