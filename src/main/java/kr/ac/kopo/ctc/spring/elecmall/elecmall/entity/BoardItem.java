package kr.ac.kopo.ctc.spring.elecmall.elecmall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter @Setter @ToString
public class BoardItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long boardId; // 글 번호

    @Column
    public String boardTitle; // 글 제목

    @Column
    public String boardContent; // 글 내용

    @Column
    public String boardAuthor; // 작성자

    @Column
    public String writeDate; // 작성일

    @Column
    public int count; // 조회수

    @Column
    public String boardType; // 글 유형 (Q: 문의, R: 후기, N: 공지)

    @ManyToOne
    @JoinColumn(name = "userId") // 고객 번호 (FK)
    private User user;

    public void patch(BoardItem boardItem) {
        if (boardItem.boardTitle != null) this.boardTitle = boardItem.boardTitle;
        if (boardItem.boardContent != null) this.boardContent = boardItem.boardContent;
    }
}
