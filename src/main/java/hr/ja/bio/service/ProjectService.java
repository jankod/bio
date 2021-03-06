package hr.ja.bio.service;


import hr.ja.bio.form.ProjectForm;
import hr.ja.bio.model.Project;
import hr.ja.bio.model.ProjectMember;
import hr.ja.bio.model.User;
import hr.ja.bio.model.util.ProjectRole;
import hr.ja.bio.parser.old.TaxonomyAbundanceResult;
import hr.ja.bio.repository.ProjectMemberRepository;
import hr.ja.bio.repository.ProjectRepository;
import hr.ja.bio.repository.UserRepository;
import hr.ja.bio.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Autowired
    UserRepository userRepository;
//    @PersistenceContext
//    private EntityManager em;

    public List<Project> getProjectsFromUser(User user) {
        return projectRepository.findAllFromUser(user.getId());
    }

    public void saveProject(ProjectForm form) {
        Project p = new Project();
        p.setName(form.getName());


        User user = SecurityUtils.getCurrentUser();
        ProjectMember projectMember = new ProjectMember();
        projectMember.setProject(p);
        projectMember.setUser(userRepository.getOne(user.getId()));
        projectMember.setRole(ProjectRole.ADMIN);
        p.addMember(projectMember);

        projectRepository.save(p);
        projectMemberRepository.save(projectMember);
    }

    public List<Project> findAllProjectOfCurrentUser() {
        User user = SecurityUtils.getCurrentUser();
        log.debug("User id {}", user.getId());
        return projectRepository.findAllFromUser(user.getId());
    }
}
