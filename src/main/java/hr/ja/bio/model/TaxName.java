package hr.ja.bio.model;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class TaxName extends AbstractPersistable<Long> implements Serializable {
	String name;
}
