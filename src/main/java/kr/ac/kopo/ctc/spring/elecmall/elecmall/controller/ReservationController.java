package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {
    @GetMapping("/reservation/new")
    public String newReservation() {
        return "/reservation/newReservation";
    }

    @GetMapping("/reservation/success")
    public String successReservation() {
        return "/reservation/successReservation";
    }
}
