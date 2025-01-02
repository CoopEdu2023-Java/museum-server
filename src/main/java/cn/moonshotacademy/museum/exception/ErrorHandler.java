package cn.moonshotacademy.museum.exception;

import cn.moonshotacademy.museum.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDto<Void> exceptionHandler(Exception e) {
        log.error("Unknown Error: " + e.getMessage());
        return ResponseDto.error(200, "Unknown Error");
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto<Void> exceptionHandler(BusinessException e) {
        log.error("Business Exception: " + e.getMessage());
        return ResponseDto.error(e.getCode(), "Error: " + e.getMessage());
    }
}
