package ru.javaops.startup.user.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.javaops.startup.common.validation.NoHtml;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProfileTo extends UserTo {

    private Map<String, @NoHtml String> contacts;

    public ProfileTo(Integer userId, String email, String firstName, String lastName) {
        super(userId, email, firstName, lastName);
    }

    public String getContact(String contactType) {
        return contacts.get(contactType);
    }
}
