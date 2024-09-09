package kr.ac.kopo.ctc.spring.elecmall.elecmall.repository;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.CommentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardItemRepository extends JpaRepository<CommentItem, Long> {
}
