package ru.javaops.startup.ref.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.javaops.startup.common.mapper.Default;
import ru.javaops.startup.common.to.CodeTo;
import ru.javaops.startup.common.validation.Code;

@Value
@EqualsAndHashCode(of = {}, callSuper = true)
@ToString(callSuper = true)
public class RefTo extends CodeTo {
    @Code
    String type;
    @NonNull
    Integer order;
    @Nullable
    @Size(max = 2048)
    String aux;

    @Default
    @JsonCreator
    public RefTo(String code, String type, int order, String aux) {
        super(null, code);
        this.type = type;
        this.order = order;
        this.aux = aux;
    }
}
