package kr.ac.kopo.ctc.spring.elecmall.elecmall.repository;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.CommentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentItemRepository extends JpaRepository<CommentItem, Long> {
    CommentItem findByCommentId(Long commentId);
    List<CommentItem> findByBoardItem_BoardId(Long boardId); // 반환 타입 수정
    List<CommentItem> deleteByBoardItem_BoardId(Long boardId);
}
