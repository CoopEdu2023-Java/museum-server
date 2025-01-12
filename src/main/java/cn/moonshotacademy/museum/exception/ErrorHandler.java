package cn.moonshotacademy.museum.exception;

import cn.moonshotacademy.museum.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseDto<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("File upload error: " + e.getMessage());
        return ResponseDto.error(4002, "File size exceeds the maximum allowed limit");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseDto<Void> exceptionHandler(Exception e) {
        log.error("Unknown Error: " + e.getMessage());
        return ResponseDto.error(1000, "Unknown Error");
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseDto<Void> exceptionHandler(BusinessException e) {
        log.error("Business Exception: " + e.getMessage());
        return ResponseDto.error(e.getCode(), "Error: " + e.getMessage());
    }
}
