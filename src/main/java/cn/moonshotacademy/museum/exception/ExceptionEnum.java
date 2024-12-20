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
    INVALID_ENTRY(3001, "Invalid entry"),
    INVALID_ENTRY_TYPE(3002, "Invalid entry type");

    private final Integer code;
    private final String message;
}
