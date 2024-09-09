package kr.ac.kopo.ctc.spring.elecmall.elecmall.repository;

import kr.ac.kopo.ctc.spring.elecmall.elecmall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(int userId);
}
