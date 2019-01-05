package hr.ja.bio.repository;

import hr.ja.bio.model.TaxonomyAbundance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface TaxonAbundanceRepository extends JpaRepository<TaxonomyAbundance, Long> {

//    @Query("from Calendar c where c.owner.id = ?1")
//    List<TaxonomyAbundance> findAllByRank1_kingdomIs(String name );
}
