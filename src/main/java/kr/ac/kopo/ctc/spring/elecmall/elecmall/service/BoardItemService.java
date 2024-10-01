package kr.ac.kopo.ctc.spring.elecmall.elecmall.service;

import jakarta.transaction.Transactional;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.BoardItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.CommentItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.CommentItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.BoardItemRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.CommentItemRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardItemService {
    private final UserRepository userRepository;
    private final BoardItemRepository boardItemRepository;
    private final CommentItemRepository commentItemRepository;

    // 전체 게시글 조회 메서드
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
    
    // 마이페이지로 내가 쓴 글 가져오기
    public List<BoardItem> getMyBoardItem(Long userId) {
        return boardItemRepository.findByUser_UserId(userId);
    }

    // 개별 게시글 조회 메서드
    public BoardItem getOneBoardItems(Long boardId) {
        return boardItemRepository.findByBoardId(boardId);
    }

    // 조회수 증가 메서드
    public void incrementViewCount(BoardItem boardItem) {
        boardItem.setCount(boardItem.getCount() + 1);
        boardItemRepository.save(boardItem);
    }

    // 작성 페이지로 이동 메서드
    public String getBoardForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        model.addAttribute("author", user.getUsername());

        return "board/new_form";
    }

    // 새 글 작성 메서드
    public void createBoard(BoardItemForm form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // 작성자를 저장
        User user = userRepository.findByUsername(username);
        form.setBoardAuthor(user.getUsername());

        // 날짜 저장
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        form.setWriteDate(formattedDate); // 고정

        BoardItem boardItem = form.toEntity(user);
        boardItem.setCount(0);

        boardItemRepository.save(boardItem);
    }

    // 수정 페이지로 이동 메서드
    public String goToEdit(Long boardId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        BoardItem dbBoardItem = boardItemRepository.findByBoardId(boardId);

        model.addAttribute("boardItem", dbBoardItem);
        model.addAttribute("author", user.getUsername());

        return "board/editBoard";
    }

    // 글 수정 메서드
    public void updateBoard(BoardItemForm form) {
        BoardItem dbBoardItem = boardItemRepository.findByBoardId(form.getBoardId());

        dbBoardItem.setBoardTitle(form.getBoardTitle());
        dbBoardItem.setBoardContent(form.getBoardContent());
        boardItemRepository.save(dbBoardItem);
    }

    // 글 삭제 메서드
    @Transactional
    public void deleteBoard(Long boardId, RedirectAttributes rttr) {
        commentItemRepository.deleteByBoardItem_BoardId(boardId);

        BoardItem dbBoardItem = boardItemRepository.findByBoardId(boardId);

        if (dbBoardItem != null) {
            boardItemRepository.delete(dbBoardItem);
            rttr.addFlashAttribute("message", " 글이 삭제되었습니다");
        }
    }

    // 수정, 삭제 권한 확인 메서드 (원글용)
    public boolean canEditOrDeleteB(BoardItem boardItem) {
        // 접속한 계정의 username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        // 글을 쓴 username
        User user = boardItem.getUser();

        boolean isAuthor = username.equals(user.getUsername());
        boolean isAdmin = role.equals("ROLE_ADMIN");

        return isAdmin || isAuthor;
    }

    // 수정, 삭제 권한 확인 메서드 (댓글용)
    public boolean canEditOrDeleteC(CommentItem commentItem) {
        // 접속한 계정의 username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        // 댓글을 쓴 username
        String commentAuthor = commentItem.getCommentAuthor();

        boolean isAuthor = username.equals(commentAuthor);
        boolean isAdmin = role.equals("ROLE_ADMIN");

        return isAdmin || isAuthor;
    }


    // 관리자 여부 확인 메서드 (원글용)
    public boolean isAdminOrNotB(BoardItem boardItem) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        boolean isAdmin = role.equals("ROLE_ADMIN");

        return isAdmin;
    }

    // 관리자 여부 확인 메서드 (댓글용)
    public boolean isAdminOrNotC(CommentItem commentItem) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        boolean isAdmin = role.equals("ROLE_ADMIN");

        return isAdmin;
    }

    // 댓글 작성 메서드
    public void createComment(Long boardId, CommentItemForm form) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // 작성자를 저장
        User user = userRepository.findByUsername(username);
        form.setCommentAuthor(user.getUsername());

        // 날짜 저장
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
        form.setCommentWriteDate(formattedDate); // 고정

        BoardItem boardItem = boardItemRepository.findByBoardId(boardId);

        CommentItem commentItem = form.toEntity(boardItem);
        commentItem.setCommentLevel(1);


        commentItemRepository.save(commentItem);
    }

    // 댓글 삭제 메서드
    public CommentItem deleteComment(Long commentId, RedirectAttributes rttr) {
        CommentItem dbCommentItem = commentItemRepository.findByCommentId(commentId);

        log.info("댓글 삭제 시도합니다 => commentItem: " + String.valueOf(dbCommentItem));

        if (dbCommentItem != null) {
            commentItemRepository.delete(dbCommentItem);
            rttr.addFlashAttribute("message", "댓글이 삭제되었습니다."); // 삭제 성공 메시지
        } else {
            rttr.addFlashAttribute("errorMessage", "댓글이 존재하지 않습니다."); // 댓글이 없을 경우 메시지
        }
        return dbCommentItem;
    }

}
