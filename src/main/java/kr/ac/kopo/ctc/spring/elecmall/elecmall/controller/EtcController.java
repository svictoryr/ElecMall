package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Reservation;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Shop;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.ShopRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.service.BoardItemService;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Controller
@Slf4j
public class EtcController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    private BoardItemService boardItemService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/event")
    public String event() {
        return "/etc/event";
    }

    @GetMapping("/myPage")
    public String myPage(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        
        User user = userRepository.findByUsername(id);
        model.addAttribute("user", user);
        
        Optional<Shop> shop = shopRepository.findById(user.getUserId());
        model.addAttribute("shop", shop);
        
        // 게시판 현황 가져오기
        List<BoardItem> myBoardItems = boardItemService.getMyBoardItem(user.getUserId());
        model.addAttribute("myBoardItems", myBoardItems);

        // 예약 목록 가져오기
        List<Reservation> myReservations = reservationService.getMyReservation(user.getUserId());
        myReservations.sort(Comparator.comparing(Reservation::getReserveDay)
                .thenComparing(Reservation::getReserveTime));
        model.addAttribute("myReservations", myReservations); // 모델에 추가

        return "/etc/myPage"; // 뷰 이름 확인
    }


}
