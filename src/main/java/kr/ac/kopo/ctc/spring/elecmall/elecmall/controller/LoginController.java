package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.JoinDTO;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class LoginController {

    @Autowired
    private JoinService joinService;

    @GetMapping("/")
    public String mainP(Model model) {

        // 사용자의 아이디를 변수로 설정
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        // 사용자의 권한을 변수로 설정하는 과정 (외워서 사용해야 될듯..)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "hello";
    }

    @GetMapping("/admin")
    public String adminP() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "/login/login";
    }

    @GetMapping("/logout")
    public String logoutP(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }


    @GetMapping("/join")
    public String joinP() {
        return "/login/join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDto) {
        System.out.println("username = " + joinDto.getUsername());
        System.out.println("password = " + joinDto.getPassword());

        joinService.joinProcess(joinDto);

        return "redirect:/login";
    }
}
