package hr.ja.bio.repository;


import hr.ja.bio.model.TaxName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxNameRepository extends JpaRepository<TaxName, Long> {

    Integer findIdByName(String taxName);
}
