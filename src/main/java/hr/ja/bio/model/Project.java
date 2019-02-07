package hr.ja.bio.model;


import hr.ja.bio.repository.MyAbstractPersistable;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString()
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@FieldNameConstants
public class Project extends MyAbstractPersistable<Long> implements Serializable {

    public enum Status {
        STARTED, FINISHED
    }

    @Column
    String name;

    @Column
    String description;

    @Column
    Boolean publicAllow;

    @Column
    @Enumerated(EnumType.STRING)
    Status status;


    @OneToMany( mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<ProjectMember> members;

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    Set<Sample> samples;



    public void addSample(Sample sample) {
        if(samples == null) {
            samples = new HashSet<>(3);
        }


        samples.add(sample);
        sample.setProject(this);
    }

    public void addMember(ProjectMember projectMember) {
        if(members == null) {
            members = new HashSet<>();
        }
        members.add(projectMember);
        projectMember.setProject(this);
    }
}
