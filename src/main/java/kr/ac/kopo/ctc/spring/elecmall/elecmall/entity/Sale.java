package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long saleId; // 세일 품목 번호

    @Column
    public String saleRate; // 할인율

    @ManyToOne
    @JoinColumn(name = "productId") // 상품 번호 (FK)
    private Product product;
}
