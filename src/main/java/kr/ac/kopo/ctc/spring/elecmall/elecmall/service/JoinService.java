package kr.ac.kopo.ctc.spring.elecmall.elecmall.service;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.dto.UserForm;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<Map<String, String>> joinProcess(UserForm userForm) {
        Map<String, String> response = new HashMap<>();

        if (userRepository.existsByUsername(userForm.getUsername())) {
            response.put("error", "이미 있는 아이디입니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!userForm.getPassword().equals(userForm.getPasswordCheck())) {
            response.put("error", "비밀번호가 다릅니다");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User data = userForm.toEntity(bCryptPasswordEncoder);
        userRepository.save(data);

        response.put("success", "회원가입 성공");
        return ResponseEntity.ok(response);
    }
}
