package kr.ac.kopo.ctc.spring.elecmall.elecmall.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("서버 오류 발생", e); // 스택 트레이스와 오류 메시지를 로그로 기록
        return new ResponseEntity<>("내부 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
