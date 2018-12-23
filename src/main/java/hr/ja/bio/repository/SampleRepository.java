package hr.ja.bio.repository;

import hr.ja.bio.model.Project;
import hr.ja.bio.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {
}
