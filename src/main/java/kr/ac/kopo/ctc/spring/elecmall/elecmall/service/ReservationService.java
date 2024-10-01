package kr.ac.kopo.ctc.spring.elecmall.elecmall.service;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.BoardItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.ReservationForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Reservation;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Shop;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.ReservationRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.ShopRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    public final UserRepository userRepository;
    public final ShopRepository shopRepository;
    public final ReservationRepository reservationRepository;
    
    // 모든 매장 조회
    public List<Shop> getAllShop() {
        List<Shop> shops;
        shops = shopRepository.findAll();

        return shops;
    }
    
    // 새 예약 신청
    public void createReservation(ReservationForm form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username);
        Shop shop = shopRepository.findById(form.getShopId()).orElseThrow(() -> new IllegalArgumentException("오류야 맨"));

        Reservation reservation = form.toEntity(user, shop);
        reservationRepository.save(reservation);
    }

    // 마이페이지로 내가 예약한 건 가져오기
    public List<Reservation> getMyReservation(Long userId) {
        return reservationRepository.findByUser_UserId(userId);
    }
}
