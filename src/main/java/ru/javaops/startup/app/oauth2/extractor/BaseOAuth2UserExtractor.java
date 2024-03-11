package ru.javaops.startup.app.oauth2.extractor;

import lombok.Setter;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class BaseOAuth2UserExtractor {
    @Setter
    private OAuth2User oAuth2User;

    private final String emailParam;
    private final String firstNameParam;
    private final String lastNameParam;

    public BaseOAuth2UserExtractor(String emailParam, String firstNameParam, String lastNameParam) {
        this.emailParam = emailParam;
        this.firstNameParam = firstNameParam;
        this.lastNameParam = lastNameParam;
    }

    public String getEmail() {
        return get(emailParam);
    }

    public String getFirstName() {
        return get(firstNameParam);
    }

    public String getLastName() {
        return get(lastNameParam);
    }

    protected <A> A get(String paramName) {
        return paramName == null ? null : oAuth2User.getAttribute(paramName);
    }
}