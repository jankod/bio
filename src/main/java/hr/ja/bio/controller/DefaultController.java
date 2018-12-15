package hr.ja.bio.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Login form
    @GetMapping("/login")
    public String login() {
        log.debug("Evo login page");
        return "login";
    }

}
