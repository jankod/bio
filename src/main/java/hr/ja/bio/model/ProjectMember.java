package hr.ja.bio.model;

import hr.ja.bio.model.util.ProjectRoleEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {})})
public class ProjectMember extends AbstractPersistable<Long> {

    @ManyToOne
    User user;

    @Enumerated(EnumType.STRING)
    ProjectRoleEnum role;

    @ManyToOne
    Project project;

}
