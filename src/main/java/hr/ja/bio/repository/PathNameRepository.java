package hr.ja.bio.repository;

import hr.ja.bio.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PathNameRepository extends JpaRepository<ProjectMember, Long> {
}
