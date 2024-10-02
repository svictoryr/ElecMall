package kr.ac.kopo.ctc.spring.elecmall.elecmall.service;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.OrderInfoForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.OrderInfo;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.OrderInfoRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.ProductRepository; // ProductRepository import
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderInfoRepository orderInfoRepository;

    @Autowired
    public ProductService(UserRepository userRepository, ProductRepository productRepository, OrderInfoRepository orderInfoRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderInfoRepository = orderInfoRepository;
    }

    // 모든 제품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // 모든 제품 반환
    }

    public List<OrderInfo> getMyProduct(Long userId) {
        return orderInfoRepository.findByUser_UserId(userId);
    }

    // 카테고리별 제품 조회
    public List<Product> getProductsByType(String productType) {
        return productRepository.findByProductType(productType); // 제품 유형으로 조회
    }

    // 특정 제품 조회
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null); // 제품 ID로 조회
    }

    // 제품 구매 처리
    public void purchaseProduct(Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        if (product.getStock() >= quantity) { // 재고가 요청 수량 이상인지 확인
            product.setStock(product.getStock() - quantity); // 재고 감소
        } else {
            throw new IllegalArgumentException("Not enough stock available for product ID: " + productId);
        }

        productRepository.save(product);
        log.info("Product purchase saved for productId: {} with quantity: {}", productId, quantity);
    }

    // 주문 처리
    public void makeOrder(Long productId, OrderInfoForm form) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        // 주문 정보 생성 및 저장
        OrderInfo orderInfo = form.toEntity(user, product);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(new Date());
        orderInfo.setOrderDate(formattedDate);

        orderInfoRepository.save(orderInfo);
    }
}
