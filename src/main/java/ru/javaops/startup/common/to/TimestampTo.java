package ru.javaops.startup.common.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TimestampTo extends BaseTo {
    boolean enabled = true;

    public TimestampTo(Integer id) {
        super(id);
    }

    @Override
    public String toString() {
        return super.toString() + ',' + enabled;
    }
}
