package ru.javaops.startup.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Date;

/**
 * For entries with start/end lifecycle (used also for enable/disable)
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class TimestampEntity extends BaseEntity {
    protected TimestampEntity(Integer id, @NonNull Date startpoint, Date endpoint) {
        super(id);
        this.startpoint = startpoint;
        this.endpoint = endpoint;
    }

    @Column(name = "startpoint", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NonNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Date startpoint = new Date();

    @Column(name = "endpoint")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Date endpoint = null;

    @JsonIgnore
    public boolean isEnabled() {
        return endpoint == null || endpoint.after(new Date());
    }

    public void setEnabled(boolean enable) {
        endpoint = enable ? null : new Date();
    }
}