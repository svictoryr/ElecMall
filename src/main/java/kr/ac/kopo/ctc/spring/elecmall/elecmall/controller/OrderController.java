package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.OrderInfoForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class OrderController {

    private final ProductService productService; // Product 서비스 의존성 주입

    @Autowired
    public OrderController(ProductService productService) {
        this.productService = productService;
    }

    // 전체 상품 조회
    @GetMapping("/order/all")
    public String showAllProduct(Model model) {
        List<Product> products = productService.getAllProducts(); // 모든 제품 조회
        model.addAttribute("products", products); // 모델에 제품 추가
        return "order/showAllProduct"; // 전체 상품 페이지 반환
    }

    @GetMapping("/order/purchase/{productId}")
    public String purchase(@PathVariable Long productId, Model model) {
        System.out.println("Received request for productId: " + productId);

        // 상품 정보 조회
        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Product not found: " + productId);
            return "error/404"; // 제품이 없는 경우 404 에러 페이지 반환
        }

        // 모델에 상품 정보 추가
        model.addAttribute("product", product);

        return "order/showProduct"; // 상품 상세 페이지로 리턴
    }


    // 구매 완료 페이지
    @PostMapping("/order/purchase")
    public String successPurchase(@RequestParam Long productId, @ModelAttribute OrderInfoForm orderInfoForm, @RequestParam int quantity) {
        productService.purchaseProduct(productId, quantity); // 제품 구매 처리
        productService.makeOrder(productId, orderInfoForm); // 주문 완료 처리
        return "redirect:/order/success"; // 구매 완료 페이지로 리다이렉트
    }


    // 구매 완료 페이지
    @GetMapping("/order/success")
    public String successProduct(Model model) {
        // 구매 완료 관련 정보 추가
        return "order/successProduct"; // 구매 완료 페이지 반환
    }
}
