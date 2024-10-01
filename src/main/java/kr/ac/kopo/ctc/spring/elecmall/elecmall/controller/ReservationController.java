package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.BoardItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.ReservationForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Reservation;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Shop;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.ReservationRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.ShopRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

    private final ShopRepository shopRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @GetMapping("/reservation/new")
    public String newReservation(Model model) {
        List<Shop> shopAll = reservationService.getAllShop();
        model.addAttribute("shopAll", shopAll);
        model.addAttribute("today", LocalDate.now());
        return "/reservation/newReservation";
    }
//  Post를 통해 값이 들어가면 됨, view 파일에서 form으로 들어가도록 name확인하고 집어넣기!!!!!!!!!!
    @PostMapping("/reservation/success")
    public String successReservation(@ModelAttribute ReservationForm form, Model model, RedirectAttributes rttr) {
        // 예약 중복 확인
        boolean exists = reservationRepository.existsByReserveDayAndReserveTimeAndShop(form.getReserveDay(), form.getReserveTime(), shopRepository.findById(form.getShopId()).orElse(null));
        log.info("exists : " + exists);

        if (exists) {
            // 에러 메시지를 모델에 추가
            rttr.addFlashAttribute("errorMessage", "이미 예약된 시간입니다. 다른 시간을 선택해주세요.");
            return "redirect:/reservation/new";
        }

        // 예약 처리
        reservationService.createReservation(form);
        return "/reservation/successReservation"; // 성공 시 성공 페이지로 이동
    }

}
