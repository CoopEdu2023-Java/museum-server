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
    ARTIFACT_ALREADY_DELETED(3002, "Artifact already deleted"),
    ARTIFACT_IS_NOT_DELETED(3003, "Artifact is not deleted"),

    EMPTY_FILE(4001, "File is empty"),
    UPLOAD_FILE_ERROR(4002, "An error occurred while uploading the file"),
    NULL_FILELIST(4004, "Cannot upload NULL filelist"),
    TYPE_NOTALLOW(4005, "This file type is not allowed"),
    NULL_FILENAME(4006, "File name cannot be null"),
    FILE_NOT_FOUND(4007, "File not found"),
    INVALID_ENTRY(5001, "Invalid entry"),
    INVALID_ENTRY_TYPE(5002, "Invalid entry type");


    private final Integer code;
    private final String message;
}
