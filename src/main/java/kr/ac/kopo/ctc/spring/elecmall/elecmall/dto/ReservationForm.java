package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
public class ReservationForm {
    private Long reserveId;
    private String reserveTime;
    private String reserveContent;

    public Reservation toEntity(User user, Shop shop) {
        Reservation reservation = new Reservation();
        reservation.setReserveId(this.reserveId);
        reservation.setReserveTime(this.reserveTime);
        reservation.setReserveContent(this.reserveContent);
        reservation.setUser(user);
        reservation.setShop(shop);

        return reservation;
    }
}
