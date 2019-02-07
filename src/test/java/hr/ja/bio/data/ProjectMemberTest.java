package hr.ja.bio.data;

import hr.ja.bio.model.Project;
import hr.ja.bio.model.ProjectMember;
import hr.ja.bio.model.User;
import hr.ja.bio.model.util.ProjectRole;
import hr.ja.bio.repository.ProjectMemberRepository;
import hr.ja.bio.repository.ProjectRepository;
import hr.ja.bio.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ProjectMemberTest {

    @Autowired
    ProjectMemberRepository projectMemberRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    User u2;
    User u1;
    Project p;
    private ProjectMember pm1;
    private ProjectMember pm2;

    @Before
    public void initData() {
        p =  new Project();
        p.setName("project1");
        u1 = new User("user1", "jdiminic@gmail.com", "passw1", User.Role.ADMIN);
        u2 = new User("user2", "jdiminic2@gmail.com", "passw2", User.Role.USER);

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.flush();


        projectRepository.saveAndFlush(p);

        pm1 = new ProjectMember();
        pm1.setUser(u1);
        pm1.setProject(p);
        pm1.setRole(ProjectRole.ADMIN);
        projectMemberRepository.save(pm1);

        pm2 = new ProjectMember();
        pm2.setUser(u1);
        pm2.setProject(p);
        pm2.setRole(ProjectRole.PARTICIPANT);
        projectMemberRepository.save(pm2);

    }

    @Test
    public void testProjectMember() {
        List<ProjectMember> all = projectMemberRepository.findAll();
        assertNotNull(all);
        assertEquals(2, all.size());

        {
            List<ProjectMember> projectMembers = projectMemberRepository.findByUserId(u1.getId());
            assertNotNull(projectMembers);
            assertEquals(2, projectMembers.size());
            assertEquals(projectMembers.get(0).getProject(), p);
            assertEquals(projectMembers.get(1).getProject(), p);
        }


    }
}
