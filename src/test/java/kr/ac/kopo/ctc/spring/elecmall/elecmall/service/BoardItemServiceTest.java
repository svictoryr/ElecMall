package kr.ac.kopo.ctc.spring.elecmall.elecmall.service;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.BoardItemForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.BoardItemRepository;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardItemServiceTest {

    @InjectMocks
    private BoardItemService boardItemService;

    @Mock
    private BoardItemRepository boardItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBoardItems() {
        BoardItem board1 = new BoardItem();
        board1.setBoardId(1L);
        board1.setBoardTitle("Test 1");

        BoardItem board2 = new BoardItem();
        board2.setBoardId(2L);
        board2.setBoardTitle("Test 2");

        when(boardItemRepository.findAll()).thenReturn(Arrays.asList(board1, board2));

        List<BoardItem> result = boardItemService.getAllBoardItems("all");

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).getBoardId()); // 최근 게시글이 먼저 나오는지 확인
    }

    @Test
    void testGetOneBoardItem() {
        BoardItem boardItem = new BoardItem();
        boardItem.setBoardId(1L);
        boardItem.setBoardTitle("Test Title");

        when(boardItemRepository.findByBoardId(1L)).thenReturn(boardItem);

        BoardItem result = boardItemService.getOneBoardItems(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBoardId());
    }

    @Test
    void testIncrementViewCount() {
        BoardItem boardItem = new BoardItem();
        boardItem.setCount(0);

        boardItemService.incrementViewCount(boardItem);

        assertEquals(1, boardItem.getCount());
        verify(boardItemRepository, times(1)).save(boardItem);
    }

    @Test
    void testDeleteBoard() {
        BoardItem boardItem = new BoardItem();
        boardItem.setBoardId(1L);

        when(boardItemRepository.findByBoardId(1L)).thenReturn(boardItem);

        boardItemService.deleteBoard(1L, redirectAttributes);

        verify(boardItemRepository, times(1)).delete(boardItem);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", " 글이 삭제되었습니다");
    }
}
