package hr.ja.bio.model;


import hr.ja.bio.repository.MyAbstractPersistable;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class PathName extends MyAbstractPersistable<Long> {

    String path;
}
