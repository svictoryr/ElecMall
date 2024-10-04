package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// 로그인
@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));
        model.addAttribute("isLoggedIn", isLoggedIn);

        
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!id.equals("anonymousUser")) {
            model.addAttribute("id", id);
        }
    }
}
