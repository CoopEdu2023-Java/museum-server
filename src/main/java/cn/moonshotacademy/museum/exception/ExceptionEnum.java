package cn.moonshotacademy.museum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    MISSING_PARAMETERS(1001, "Missing Parameters"),

    USER_EXISTS(2001, "User exists"),
    USER_NOT_FOUND(2002, "User not found"),
    WRONG_PASSWORD(2003, "Wrong password"),

    EMPTY_FILE(3001, "File is empty"),
    UPLOAD_FILE_ERROR(3002, "An error occurred while uploading the file");

    private final Integer code;
    private final String message;
}
