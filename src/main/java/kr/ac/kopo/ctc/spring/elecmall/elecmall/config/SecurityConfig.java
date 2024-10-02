package kr.ac.kopo.ctc.spring.elecmall.elecmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean // 비밀번호 암호화
    public BCryptPasswordEncoder bCryptPasswordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean // 권한을 계층으로 설정
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_VIP\n" +
                "ROLE_VIP > ROLE_USER");

        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http // 인가
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll()
                        .requestMatchers("/elecmall/**").permitAll() // CSS 파일 접근 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/myPage/**").hasAnyRole("USER")
                        .requestMatchers("/reservation/**").hasAnyRole("USER")
                        .anyRequest().authenticated()
                );

        http // 로그인 홈페이지에 대한 자동 작업 (controller에 만들 필요 없음)
                .formLogin((auth) -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

//        http // csrf 공격 보호하는 것을 해제 (과제 중일때는 사용했었음)
//                .csrf((auth) -> auth.disable());

        http // 중복 로그인
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // 다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(true)); // trur면 새로운 로그인을 막고, false면 하나를 삭제하고 로그인

        http // 해커가 정보를 탈취하지 못하도록 '세션 고정 보호'
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());
        
        http // 로그아웃
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
