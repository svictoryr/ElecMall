package kr.ac.kopo.ctc.spring.elecmall.elecmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ElecmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElecmallApplication.class, args);


		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	}
}
