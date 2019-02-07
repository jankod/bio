package hr.ja.bio.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hr.ja.bio.model.User;
import hr.ja.bio.model.User.Role;
import hr.ja.bio.repository.UserRepository;

//@SpringBootApplication
@TestPropertySource("classpath:test.properties")
@DataJpaTest
@RunWith(SpringRunner.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TestData1 {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testUser() {
		User u1 = new User("user1", "jdiminic@gmail.com", "passw1", Role.ADMIN);
		userRepository.save(u1);
		
		User u1_double = userRepository.findByUsername("user1");
		assertEquals(u1, u1_double);
		
		assertNull(userRepository.findByUsername("no name"));
		
	}
}
