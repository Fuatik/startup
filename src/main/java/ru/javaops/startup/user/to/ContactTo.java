package ru.javaops.startup.user.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.javaops.startup.common.HasCode;
import ru.javaops.startup.common.validation.Code;
import ru.javaops.startup.common.validation.NoHtml;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactTo implements HasCode {
    @Code
    private String code;
    @NotBlank
    @Size(min = 2, max = 256)
    @NoHtml
    private String value;
}
