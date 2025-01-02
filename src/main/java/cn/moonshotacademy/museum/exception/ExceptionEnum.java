package cn.moonshotacademy.museum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    MISSING_PARAMETERS(1001, "Missing Parameters"),
    ILLEGAL_PARAMETERS(1002, "Illegal Parameters"),

    USER_EXISTS(2001, "User exists"),
    USER_NOT_FOUND(2002, "User not found"),
    WRONG_PASSWORD(2003, "Wrong password"),

    ARTIFACT_NOT_FOUND(3001, "Artifact not found"),

    EMPTY_FILE(4001, "File is empty"),
    UPLOAD_FILE_ERROR(4002, "An error occurred while uploading the file"),

    INVALID_ENTRY(5001, "Invalid entry"),
    INVALID_ENTRY_TYPE(5002, "Invalid entry type");

    private final Integer code;
    private final String message;
}
