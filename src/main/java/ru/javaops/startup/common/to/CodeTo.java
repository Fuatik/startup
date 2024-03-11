package ru.javaops.startup.common.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.NonNull;
import ru.javaops.startup.common.HasCode;
import ru.javaops.startup.common.validation.Code;

@Getter
@EqualsAndHashCode(of = {"code"}, callSuper = true)
public class CodeTo extends TimestampTo implements HasCode {
    @Code
    @NonNull
    String code;

    public CodeTo(Integer id, @NonNull String code) {
        super(id);
        this.code = code;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + code + "(" + enabled + ")";
    }
}