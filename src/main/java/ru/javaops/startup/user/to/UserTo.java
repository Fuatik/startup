package ru.javaops.startup.user.to;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.javaops.startup.common.HasIdAndEmail;
import ru.javaops.startup.common.to.TimestampTo;
import ru.javaops.startup.common.validation.NoHtml;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserTo extends TimestampTo implements HasIdAndEmail {
    @Email
    @NotBlank
    @Size(max = 64)
    @NoHtml  // https://stackoverflow.com/questions/17480809
    String email;

    @NotBlank
    @Size(max = 32)
    @NoHtml
    String firstName;

    @Size(max = 32)
    @Nullable
    @NoHtml
    String lastName;

    public UserTo(Integer id, String email, String firstName, String lastName) {
        super(id);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserTo:" + id + '[' + email + ']';
    }
}
