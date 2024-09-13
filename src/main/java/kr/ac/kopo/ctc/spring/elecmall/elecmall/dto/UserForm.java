package kr.ac.kopo.ctc.spring.elecmall.elecmall.dto;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@Getter @Setter @ToString
public class UserForm {
    private Long userId;
    private String username;
    private String realname;
    private String password;
    private String passwordCheck;
    private String userTelnum;
    private String userAddress;
    private String role;
    private String smsOnOff;

    public User toEntity(BCryptPasswordEncoder encoder) {
        User user = new User();
        user.setUserId(this.userId);
        user.setUsername(this.username);
        user.setRealname(this.realname);
        user.setPassword(encoder.encode(this.password));
        user.setPasswordCheck(encoder.encode(this.passwordCheck));
        user.setUserTelnum(this.userTelnum);
        user.setUserAddress(this.userAddress);
        user.setRole("ROLE_USER");
        user.setSmsOnOff("smsOn".equals(this.smsOnOff) ? "O" : "X");

        return user;
    }
}
