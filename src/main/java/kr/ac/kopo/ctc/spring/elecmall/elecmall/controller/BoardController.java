package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.BoardItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.CommentItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.CommentItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.CommentItemRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.service.BoardItemService;
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

    @Autowired
    private CommentItemRepository commentItemRepository;

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
        List<CommentItem> comments = commentItemRepository.findByBoardItem_BoardId(boardId);

        // 조회수 증가
        boardItemService.incrementViewCount(boardItemEntity);

        // model로 넘길 값들 지정
        boolean canEditOrDeleteB = boardItemService.canEditOrDeleteB(boardItemEntity); // 원글을 삭제하거나 수정할 권한
        boolean isAdmin = boardItemService.isAdminOrNotB(boardItemEntity); // 관리자 여부
        boolean canEditOrDeleteC = comments.stream()
                .anyMatch(comment -> boardItemService.canEditOrDeleteC(comment)); // 댓글을 삭제하거나 수정할 권한

        model.addAttribute("boardItem", boardItemEntity);
        model.addAttribute("canEditOrDeleteB", canEditOrDeleteB);
        model.addAttribute("canEditOrDeleteC", canEditOrDeleteC);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("comments", comments);

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
    public String editBoard(@PathVariable Long boardId, Model model, RedirectAttributes rttr) {
        BoardItem boardItem = boardItemService.getOneBoardItems(boardId);

        if (!boardItemService.canEditOrDeleteB(boardItem)) {
            rttr.addFlashAttribute("errorMessage", "수정할 권한이 없습니다.");
            return "redirect:/board/list";
        }

        boardItemService.canEditOrDeleteB(boardItem);
        boardItemService.isAdminOrNotB(boardItem);
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
        BoardItem boardItem = boardItemService.getOneBoardItems(boardId);

        // 권한 체크
        if (!boardItemService.canEditOrDeleteB(boardItem)) {
            rttr.addFlashAttribute("errorMessage", "삭제할 권한이 없습니다.");
            return "redirect:/board/list";
        }

        boardItemService.deleteBoard(boardId, rttr);
        return "redirect:/board/list";
    }

    // 댓글 작성
    @PostMapping("/board/{boardId}/comment")
    public String createComment(@PathVariable Long boardId, @ModelAttribute CommentItemForm form) {
        boardItemService.createComment(boardId, form); // boardId를 전달
        return "redirect:/board/" + boardId; // redirect URL 수정
    }

    // 댓글 삭제
    @GetMapping("/board/{boardId}/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long commentId, RedirectAttributes rttr) {
        // 댓글 아이템 가져오기
        CommentItem commentItem = commentItemRepository.findByCommentId(commentId); // 댓글을 가져오는 메서드 추가 필요

        // 권한 체크
        if (!boardItemService.canEditOrDeleteC(commentItem)) {
            rttr.addFlashAttribute("errorMessage", "삭제할 권한이 없습니다.");
            return "redirect:/board/" + boardId; // boardId로 리다이렉트
        }

        boardItemService.deleteComment(commentId, rttr); // 삭제 로직 실행

        return "redirect:/board/" + boardId; // boardId로 리다이렉트
    }
}
