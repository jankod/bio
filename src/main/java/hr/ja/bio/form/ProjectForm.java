package hr.ja.bio.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProjectForm {

    @NotNull
    @Size(min = 2, max = 200)
    String name;
}
