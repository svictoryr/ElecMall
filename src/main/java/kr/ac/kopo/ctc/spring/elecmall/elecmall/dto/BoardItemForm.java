//package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;
//
//import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.BoardItem;
//import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.CommentItem;
//import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@AllArgsConstructor
//@Getter @Setter @ToString
//public class BoardItemForm {
//    private Long boardId;
//    public String boardTitle;
//    public String boardContent;
//    public String boardAuthor;
//    public String writeDate;
//    public int count;
//    public String boardType;
//
//    public BoardItem toEntity(User user) {
//        BoardItem boardItem = new BoardItem();
//        boardItem.setBoardId(this.boardId);
//        boardItem.setBoardTitle(this.boardTitle);
//        boardItem.setBoardContent(this.boardContent);
//        boardItem.setBoardAuthor(this.boardAuthor);
//        boardItem.setWriteDate(this.writeDate);
//        boardItem.setCount(this.count);
//        boardItem.setBoardType(this.boardType);
//        boardItem.setUser(user);
//
//        return boardItem;
//    }
//}
