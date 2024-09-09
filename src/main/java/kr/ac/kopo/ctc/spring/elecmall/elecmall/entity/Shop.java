package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long shopId; //  매장 번호

    @Column
    public String shopName; // 매장명

    @Column
    public String shopAddress; // 매장 주소

    @Column
    public String openTime; // 개점 시간

    @Column
    public String closeTime; // 폐점 시간

    @Column
    public String shopTelnum; // 매장 전화번호
}
