package hr.ja.bio;

import hr.ja.bio.repository.UserRepository;
import org.junit.Before;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Before
    public void setUp() {
        initMocks(this);
    }
}
