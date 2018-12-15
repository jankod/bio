package hr.ja.bio.repository;

import hr.ja.bio.model.TaxonAbundance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxonAbundanceRepository extends JpaRepository<TaxonAbundance, Long> {

//    @Query("from Calendar c where c.owner.id = ?1")
//    List<Calendar> findAllByUserId(Long userId);
}
