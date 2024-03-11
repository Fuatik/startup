package ru.javaops.startup.ref.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.javaops.startup.common.HasCode;
import ru.javaops.startup.common.mapper.Default;
import ru.javaops.startup.common.model.TimestampEntity;
import ru.javaops.startup.common.validation.Code;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ref")
public class RefEntity extends TimestampEntity implements HasCode {

    @Column(name = "code", nullable = false, unique = true)
    @Code
    private String code;

    @Column(name = "type")
    @Code
    private String type;

    @Column(name = "ref_order", nullable = false)
    @NonNull
    Integer order;

    @Column(name = "aux")
    @Nullable
    @Size(max = 2048)
    private String aux;

    @Default
    public RefEntity(String code, String type, Integer order, String aux) {
        this.code = code;
        this.type = type;
        this.order = order;
        this.aux = aux;
    }

    @Override
    public String toString() {
        return "RefEntity:" + code;
    }
}