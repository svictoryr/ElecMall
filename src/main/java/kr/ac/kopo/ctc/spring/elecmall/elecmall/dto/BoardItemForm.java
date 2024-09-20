package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter @Setter @ToString
public class BoardItemForm {
    private Long boardId;
    public String boardTitle;
    public String boardContent;
    public String boardAuthor;
    public String writeDate;
    public Integer count;
    public String boardType;

    public BoardItem toEntity(User user) {
        BoardItem boardItem = new BoardItem();
        boardItem.setBoardId(this.boardId); // 없음
        boardItem.setBoardTitle(this.boardTitle); // 작성
        boardItem.setBoardContent(this.boardContent); // 작성
        boardItem.setBoardAuthor(this.boardAuthor); // 고정
        boardItem.setWriteDate(this.writeDate); // 고정
        boardItem.setCount(this.count != null ? this.count : 0); // 고정
        boardItem.setBoardType(this.boardType); // 작성
        boardItem.setUser(user); // 고정

        return boardItem;
    }
}
