package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping("/board/all")
    public String showAllBoard() {
        return "/board/showAllBoard";
    }

    @GetMapping("/board/")
    public String showBoard() {
        return "/board/showAllBoard";
    }

    @GetMapping("/board/new")
    public String newBoard() {
        return "/board/newBoard";
    }

    @GetMapping("/board/edit")
    public String editBoard() {
        return "/board/editBoard";
    }
}
