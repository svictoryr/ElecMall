package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EtcController {
    @GetMapping("/event")
    public String event() {
        return "/etc/event";
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "/etc/myPage";
    }
}
