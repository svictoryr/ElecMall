package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/order/all")
    public String showAllProduct() {
        return "/order/showAllProduct";
    }

    @GetMapping("order/purchase")
    public String purchase() {
        return "/order/showProduct";
    }

    @GetMapping("order/success")
    public String successProduct() {
        return "/order/successProduct";
    }
}
