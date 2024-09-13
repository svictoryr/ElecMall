//package kr.ac.kopo.ctc.spring.elecmall.elecmall.service;
//
//import kr.ac.kopo.ctc.spring.elecmall.elecmall.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor // 이게 Autowired 같은 거임
//public class LoginService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("접속 중인 아이디 : {}", username);
//
//        kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        log.info("User found : {} ", user);
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                new ArrayList<>()
//        );
//    }
//}
