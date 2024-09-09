package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter @Setter @ToString
public class UserForm {
    private Long userId;
    private String userName;
    private String realName;
    private String password;
    private String passwordCheck;
    private String userTelnum;
    private String userAddress;
    private String userType;
    private String smsOnOff;

    public User toEntity() {
        User user = new User();
        user.setUserId(this.userId);
        user.setUserName(this.userName);
        user.setRealName(this.realName);
        user.setPassword(this.password);
        user.setPasswordCheck(this.passwordCheck);
        user.setUserTelnum(this.userTelnum);
        user.setUserAddress(this.userAddress);
        user.setUserType(this.userType);
        user.setSmsOnOff(this.smsOnOff);

        return user;
    }
}
