//package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Entity
//@Getter @Setter @ToString
//public class Reservation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public Long reserveId; // 예약번호
//
//    @Column
//    public String reserveTime; // 예약 시간
//
//    @Column
//    public String reserveContent; // 요구사항
//
//    @ManyToOne
//    @JoinColumn(name = "userId") // 고객 번호 (FK)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "shopId") // 매장 번호 (FK)
//    private Shop shop;
//}
