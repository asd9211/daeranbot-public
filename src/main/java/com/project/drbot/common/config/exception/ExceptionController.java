package com.project.drbot.common.config.exception;

import com.project.drbot.common.config.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * Client에게 exception을 반환합니다.
     *
     * @param e Exception 정보
     * @return exception message
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Map<String, Object>> serviceExceptionResponse(ServiceException e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
