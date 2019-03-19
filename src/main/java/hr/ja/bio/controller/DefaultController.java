package hr.ja.bio.controller;

import hr.ja.bio.model.User;
import hr.ja.bio.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class DefaultController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(Model mode) {
        List<User> users = userRepository.findAll();
        mode.addAttribute("users", users);
        return "index";
    }

    // Login form
    @GetMapping("/login")
    public String login(HttpServletRequest request) {

        log.debug("Evo login page " + request.getRequestURI());
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }




}
