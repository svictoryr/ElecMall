package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long productId; // 상품 번호

    @Column
    public String productName; // 상품명

    @Column
    public String productContent; // 상품 설명

    @Column
    public String productType; // 상품 타입

    @Column
    public int price; // 상품 가격

    @Column
    public int stock; // 재고 수량

    @Column
    public String imageUrl; // 이미지 URL

    @Column
    public boolean isSoldOut = false; // 기본값을 false로 설정

    // 품절 여부를 설정하는 메서드
    public void setIsSoldOut() {
        this.isSoldOut = (this.stock == 0); // 재고가 0이면 true, 아니면 false
    }
}
