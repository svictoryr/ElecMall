package kr.ac.kopo.ctc.spring.elecmall.elecmall.repository;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardItemRepository extends JpaRepository<BoardItem, Long> {
    BoardItem findByBoardId(Long boardId);
    List<BoardItem> findByBoardType(String boardType);
    List<BoardItem> findByUser_UserId(Long userId);
}
