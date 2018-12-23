package hr.ja.bio.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends AbstractPersistable<Long> {


    @Column(nullable = false, unique = true)
    String username;

    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    @Column(nullable = false)
    String email;


    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @Column
    String password;


    //    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Role role;


    public enum Role  {
        ADMIN, USER
    }
}
