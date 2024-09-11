package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/login")
    public String login() {
        return "/login/login";
    }

    @GetMapping("/newAccount")
    public String newAccount() {
        return "/login/newAccount";
    }

    @GetMapping("/test")
    public String test() {
        return "/layouts/footer";
    }
}
