package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/")
    public String main() {
        return "main";
    }
}
