package ru.javaops.startup.ref.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.javaops.startup.common.HasCode;

@AllArgsConstructor
@Getter
public enum RefType implements HasCode {
    CONTACT("C");
    private final String code;

    @Override
    public String toString() {
        return name() + '(' + code + ')';
    }
}
