package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.OrderInfoForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
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
    public String showAllProduct(@RequestParam(value="type", required = false) String type, Model model) {
        List<Product> products = productService.getAllProducts(type); // 모든 제품 조회

    // 재고 상태에 따라 품절 여부 설정
        for (Product product : products) {
            if (product.getStock() == 0) {
                product.setIsSoldOut();
            }
        }

        model.addAttribute("products", products); // 모델에 제품 추가
        model.addAttribute("isTv", "TV/영상가전".equals(type));
        model.addAttribute("isRefrigerator", "냉장고".equals(type));
        model.addAttribute("isDrum", "세탁기".equals(type));
        model.addAttribute("isManager", "의류관리기".equals(type));
        model.addAttribute("isDrier", "건조기".equals(type));

        return "order/showAllProduct"; // 전체 상품 페이지 반환
    }

    @GetMapping("/order/purchase/{productId}")
    public String purchase(@PathVariable Long productId, Model model) {
        // 상품 정보 조회
        Product product = productService.getProductById(productId);

        if (product.getStock() == 0) {
            product.setIsSoldOut();
        }

        // 모델에 상품 정보 추가
        model.addAttribute("product", product);
        model.addAttribute("isSoldOut", product.isSoldOut()); // 추가
        log.info("isSoldOut : " + product.isSoldOut());

        return "order/showProduct"; // 상품 상세 페이지로 리턴
    }


    // 구매 완료 페이지
    @PostMapping("/order/purchase")
    public String successPurchase(@RequestParam Long productId, @ModelAttribute OrderInfoForm orderInfoForm, @RequestParam int quantity) {
        log.info("1. orderInfoForm : " + orderInfoForm);
        log.info("2. quantity : " + quantity);
        log.info("3. productId : " + productId);


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
