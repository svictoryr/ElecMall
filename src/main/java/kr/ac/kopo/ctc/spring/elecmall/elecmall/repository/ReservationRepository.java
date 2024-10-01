package kr.ac.kopo.ctc.spring.elecmall.elecmall.repository;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Reservation;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser_UserId(Long userId);
    boolean existsByReserveDayAndReserveTimeAndShop(String reserveDay, String reserveTime, Shop shop);
}
