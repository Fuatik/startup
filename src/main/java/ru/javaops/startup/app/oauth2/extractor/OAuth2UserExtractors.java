package ru.javaops.startup.app.oauth2.extractor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuth2UserExtractors {
    @Bean("github")
    BaseOAuth2UserExtractor githubExtractor() {
        return new BaseOAuth2UserExtractor("email", "name", null) {
            @Override
            public String getFirstName() {
                String name = super.getFirstName();
                return name != null ? name : get("login");
            }
        };
    }

    @Bean("gitlab")
    BaseOAuth2UserExtractor gitlabExtractor() {
        return new BaseOAuth2UserExtractor("email", "name", null) {
            @Override
            public String getFirstName() {
                String name = super.getFirstName();
                return name != null ? name : get("username");
            }
        };
    }

    @Bean("yandex")
    BaseOAuth2UserExtractor yandexExtractor() {
        return new BaseOAuth2UserExtractor("default_email", "first_name", "last_name");
    }

    @Bean("google")
    BaseOAuth2UserExtractor googleExtractor() {
        return new BaseOAuth2UserExtractor("email", "given_name", "family_name");
    }
}
