package hr.ja.bio.repository;

import hr.ja.bio.model.Project;
import hr.ja.bio.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {

    @Query
    Sample findByName(String sampleName);
}
