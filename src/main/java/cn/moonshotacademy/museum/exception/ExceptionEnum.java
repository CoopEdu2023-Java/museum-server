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
    ALREADY_DELETED(2004, "Artifact already deleted"),
    IS_NOT_DELETED(2005, "Artifact is not deleted"),
    ARTIFACT_NOT_FOUND(2005, "Artifact not found");

    private final Integer code;
    private final String message;
}
