package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.CommentItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.OrderInfo;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
public class CommentItemForm {
    private Long commentId;
    private String commentAuthor;
    private String commentComment;
    private String commentWriteDate;
    private int commentLevel;


    public CommentItem toEntity(BoardItem boardItem) {
        CommentItem commentItem = new CommentItem();
        commentItem.setCommentId(this.commentId);
        commentItem.setCommentAuthor(this.commentAuthor);
        commentItem.setCommentComment(this.commentComment);
        commentItem.setCommentWriteDate(this.commentWriteDate);
        commentItem.setCommentLevel(this.commentLevel);
        commentItem.setBoardItem(boardItem);

        return commentItem;
    }
}
