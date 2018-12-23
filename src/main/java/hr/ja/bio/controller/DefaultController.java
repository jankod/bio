package hr.ja.bio.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class DefaultController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Login form
    @GetMapping("/login")
    public String login(HttpServletRequest request) {

        log.debug("Evo login page "+ request.getRequestURI());
        return "login";
    }

}
