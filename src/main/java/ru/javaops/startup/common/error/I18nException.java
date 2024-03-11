package ru.javaops.startup.common.error;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Map.Entry;

import static java.util.Map.entry;
import static ru.javaops.startup.common.error.ErrorType.NOT_FOUND;

@Getter
public class I18nException extends AppException {
    public static final Entry<String, ErrorType> ENTITY_NOT_FOUND = entry("err.msg.entityNotFound", NOT_FOUND);
    public static final Entry<String, ErrorType> USER_NOT_FOUND = entry("err.msg.userNotFound", NOT_FOUND);
    public static final Entry<String, ErrorType> REF_NOT_FOUND = entry("err.msg.refNotFound", NOT_FOUND);

    private final Object[] params;

    public I18nException(Entry<String, ErrorType> entry, Object... params) {
        this(entry.getKey(), entry.getValue(), params);
    }

    public I18nException(@NonNull String code, ErrorType errorType, Object... params) {
        super(code, errorType);
        this.params = params;
    }

    public String getCode() {
        return getMessage();
    }
}
