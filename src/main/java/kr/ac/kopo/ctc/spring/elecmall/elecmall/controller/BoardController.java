package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import jakarta.servlet.http.HttpSession;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.BoardItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.service.BoardItemService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private BoardItemService boardItemService;

    // 전체 게시글 조회
    @GetMapping("/board/list")
    public String showAllBoard(@RequestParam(value="type", required = false) String type, Model model) {
        List<BoardItem> boardItemAll = boardItemService.getAllBoardItems(type);

        model.addAttribute("boardItemAll", boardItemAll);
        model.addAttribute("isQuestion", "q".equals(type));
        model.addAttribute("isReview", "r".equals(type));
        model.addAttribute("isNotice", "n".equals(type));

        return "board/showAllBoard";
    }

    // 개별 게시글 조회
    @GetMapping("/board/{boardId}")
    public String showBoard(@PathVariable Long boardId, Model model) {
        BoardItem boardItemEntity = boardItemService.getOneBoardItems(boardId);
        log.info("개별 게시글" + String.valueOf(boardItemEntity));

        boardItemService.incrementViewCount(boardItemEntity);

//        boolean canEditOrDelete = boardItemService.canEditOrDelete(boardItemEntity, loggedInUser);
//        boolean isAdmin = boardItemService.isAdmin(boardItemEntity, loggedInUser);

        log.info(boardItemEntity.toString());
        model.addAttribute("boardItem", boardItemEntity);
//        model.addAttribute("canEditOrDelete", canEditOrDelete);
//        model.addAttribute("isAdmin", isAdmin);

        return "board/showBoard";
    }

    // 작성 페이지로
    @GetMapping("/board/new_form")
    public String newBoard(Model model) {
        return boardItemService.getBoardForm(model);
    }

    // 새 글 작성
    @PostMapping("/board/create")
    public String createBoard(@ModelAttribute BoardItemForm form) {
        boardItemService.createBoard(form);
        return "redirect:/board/list";
    }
    
    // 수정 페이지로
    @GetMapping("/board/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId, Model model) {
        return boardItemService.goToEdit(boardId, model);
    }

    // 글 수정
    @PostMapping("/board/update")
    public String updateBoard(@ModelAttribute BoardItemForm form) {
        boardItemService.updateBoard(form);
        return "redirect:/board/" + form.getBoardId();
    }

    // 글 삭제
    @GetMapping("board/{boardId}/delete")
    public String deleteBoard(@PathVariable Long boardId, RedirectAttributes rttr) {
        boardItemService.deleteBoard(boardId, rttr);
        return "redirect:/board/list";
    }
}
