package hr.ja.bio.service;


import hr.ja.bio.model.Project;
import hr.ja.bio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    public List<Project> getMyProjects() {
        return projectRepository.findAll();
    }
}
