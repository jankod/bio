package hr.ja.bio.model;

import hr.ja.bio.model.util.ProjectRole;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "project_id"})})
@Table
public class ProjectMember extends AbstractPersistable<Long> implements Serializable {

    @ManyToOne(optional = false)
    User user;

    @Enumerated(EnumType.STRING)
    ProjectRole role;

    @ManyToOne(optional = false)
    Project project;


}
