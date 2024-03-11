package ru.javaops.startup.common.error;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

public enum ErrorType {
    APP_ERROR("err.type.appError", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_DATA("err.type.badData", HttpStatus.UNPROCESSABLE_ENTITY),
    BAD_REQUEST("err.type.badRequest", HttpStatus.UNPROCESSABLE_ENTITY),
    DATA_CONFLICT("err.type.dataConflict", HttpStatus.CONFLICT),
    NOT_FOUND("err.type.notFound", HttpStatus.NOT_FOUND),
    AUTH_ERROR("err.type.authError", HttpStatus.FORBIDDEN),
    UNAUTHORIZED("err.type.unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("err.type.forbidden", HttpStatus.FORBIDDEN);

    ErrorType(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    public static ErrorType of(HttpStatus status) {
        return Arrays.stream(values()).filter(et -> et.status == status).findAny().orElse(APP_ERROR);
    }

    public final String code;
    public final HttpStatus status;
}
