package hr.ja.bio.data;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hr.ja.bio.model.User;
import hr.ja.bio.model.User.Role;
import hr.ja.bio.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.*;

	@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserJpaTest {

	@Autowired private TestEntityManager entityManager;

	@Autowired private UserRepository userRepository;

	private User u1;

	private User u2;

	@Before
	public void prepareData() {
		u1 = new User("user1", "jdiminic@gmail.com", "passw1", Role.ADMIN);
		u2 = new User("user2", "jdiminic2@gmail.com", "passw2", Role.USER);
		userRepository.save(u1);
		userRepository.save(u2);
	}

	@After
	public void clearData() {
		userRepository.deleteAll();
	}

	@Test
	public void testFindUsername() {
		User u1_double = userRepository.findByUsername("user1");
		assertEquals(u1, u1_double);

		assertNull(userRepository.findByUsername("no name"));

	}

	@Test
	public void testFindMyEmail() {
		Optional<User> user = userRepository.findByEmail("jdiminic2@gmail.com");
		assertEquals("user2", user.get().getUsername());
		assertTrue(user.isPresent());
	}
}
