package kr.ac.kopo.ctc.spring.elecmall.elecmall.service;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.BoardItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.BoardItemRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardItemService {
    private final BoardItemRepository boardItemRepository;
    private final UserRepository userRepository;

    public List<BoardItem> getAllBoardItems(String type) {
        List<BoardItem> boardItems;

        if ("all".equals(type) || type == null) {
            boardItems = boardItemRepository.findAll();
        } else {
            boardItems = boardItemRepository.findByBoardType(type);
        }

        return boardItems.stream()
                .sorted(Comparator.comparingLong(BoardItem::getBoardId).reversed())
                .collect(Collectors.toList());
    }

    public BoardItem getOneBoardItems(Long boardId) {
        return boardItemRepository.findByBoardId(boardId);
    }

    public void incrementViewCount(BoardItem boardItem) {
        boardItem.setCount(boardItem.getCount() + 1);
        boardItemRepository.save(boardItem);
    }

    public String getBoardForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        model.addAttribute("author", user.getUsername());

        return "board/new_form";
    }

    public void createBoard(BoardItemForm form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // 작성자를 저장
        User user = userRepository.findByUsername(username);
        form.setBoardAuthor(user.username);
        
        // 날짜 저장
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        form.setWriteDate(formattedDate); // 고정

        BoardItem boardItem = form.toEntity(user);
        boardItem.setCount(0);

        boardItemRepository.save(boardItem);
        log.info("회원이 게시글을 작성하였습니다: {}님", username);
    }

    public String goToEdit(Long boardId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        BoardItem dbBoardItem = boardItemRepository.findByBoardId(boardId);

        model.addAttribute("boardItem", dbBoardItem);
        model.addAttribute("author", user.getUsername());

        return "board/editBoard";
    }

    public void updateBoard(BoardItemForm form) {
        BoardItem dbBoardItem = boardItemRepository.findByBoardId(form.getBoardId());

        dbBoardItem.setBoardTitle(form.getBoardTitle());
        dbBoardItem.setBoardContent(form.getBoardContent());
        boardItemRepository.save(dbBoardItem);
    }

    public void deleteBoard(Long boardId, RedirectAttributes rttr) {
        BoardItem dbBoardItem = boardItemRepository.findByBoardId(boardId);

        if (dbBoardItem != null) {
            boardItemRepository.delete(dbBoardItem);
            rttr.addFlashAttribute("message", " 글이 삭제되었습니다");
        }
    }
}
