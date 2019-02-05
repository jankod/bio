package hr.ja.bio.repository;

import hr.ja.bio.model.Project;
import hr.ja.bio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    @Query
    List<Project> findAllByMembersUserId(Long id);



}
