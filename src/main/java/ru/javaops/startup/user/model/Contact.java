package ru.javaops.startup.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.javaops.startup.common.HasCode;
import ru.javaops.startup.common.validation.Code;
import ru.javaops.startup.common.validation.NoHtml;

import java.io.Serializable;
import java.util.Objects;

import static ru.javaops.startup.common.util.Util.getEffectiveClass;

/**
 * id is the same as User.id (not autogenerate)
 */
@Entity
@Table(name = "contact")
@IdClass(Contact.ContactId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contact implements HasCode {
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    // link to Ref.code with RefType.CONTACT
    @Id
    @Code
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "c_value", nullable = false)
    @NotBlank
    @Size(min = 2, max = 256)
    @NoHtml
    private String value;

    @Data
    @NoArgsConstructor
    static class ContactId implements Serializable {
        private int userId;
        private String code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getEffectiveClass(this) != getEffectiveClass(o)) return false;
        Contact contact = (Contact) o;
        return userId == contact.userId && Objects.equals(code, contact.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, code);
    }
}
