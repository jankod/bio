package hr.ja.bio.model;

import hr.ja.bio.repository.MyAbstractPersistable;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class TaxName extends MyAbstractPersistable<Long> implements Serializable {
	String name;
}
