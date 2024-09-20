package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class CommentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long commentId; // 댓글 번호

    @Column
    public String commentAuthor; // 댓글 작성자

    @Column
    public String commentComment; // 댓글 내용

    @Column
    public String commentWriteDate; // 댓글 작성일

    @Column
    public int commentLevel; // 대댓글 레벨

    @ManyToOne
    @JoinColumn(name = "boardId") // 원 글 번호 (FK)
    private BoardItem boardItem;
}
