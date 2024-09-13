package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long userId; // 고객번호

    @Column
    public String realname; // 유저 본명

    @Column(unique = true)
    public String username; // 계정 ID

    @Column
    public String password; // 비밀번호

    @Column
    public String passwordCheck; // 비밀번호 확인

    @Column
    public String userTelnum; // 고객 연락처

    @Column
    public String userAddress; // 고객 주소

    @Column
    public String role; // 고객 유형 (NEWCOMMER, EMPLOYEE, ADMIN)

    @Column
    public String smsOnOff; // 알람 수신 여부
}
