package hr.ja.bio.service;

import hr.ja.bio.model.util.MyUserDetails;
import hr.ja.bio.model.User;
import hr.ja.bio.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    BCryptPasswordEncoder passwordEncoder;


    public MyUserDetailsService(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Trazim usera: "+ username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("Nisam nasao");
            throw new UsernameNotFoundException(username);
        }
        log.debug("Nasao {}", user);
        return new MyUserDetails(user);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
