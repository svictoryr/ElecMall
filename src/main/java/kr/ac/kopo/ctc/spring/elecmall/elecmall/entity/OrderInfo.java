package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long orderId; // 주문 번호

    @Column
    public String orderDate; // 주문일자

    @Column
    public String orderType; // 주문 상황 (s: 배송시작, g: 배송중, d: 배송완료)

    @Column
    public int Quantity; // 주문 수량

    @Column
    public int totalPrice; // 총 계산 금액

    @ManyToOne
    @JoinColumn(name = "userId") // 고객 번호 (FK)
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId") // 상품 번호 (FK)
    private Product product;
}
