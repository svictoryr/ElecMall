package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import jakarta.persistence.Column;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
public class ReservationForm {
    private Long reserveId;
    private String reserveDay;
    private String reserveTime;
    private String reserveCategory;
    private String reserveContent;
    private Long userId;
    private Long shopId;

    public Reservation toEntity(User user, Shop shop) {
        Reservation reservation = new Reservation();
        reservation.setReserveId(this.reserveId);
        reservation.setReserveDay(this.reserveDay);
        reservation.setReserveTime(this.reserveTime);
        reservation.setReserveContent(this.reserveContent);
        reservation.setReserveCategory(this.reserveCategory);
        reservation.setReserveContent(this.reserveContent);
        reservation.setUser(user);
        reservation.setShop(shop);

        return reservation;
    }
}
