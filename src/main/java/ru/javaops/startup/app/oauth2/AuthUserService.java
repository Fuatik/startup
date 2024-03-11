package ru.javaops.startup.app.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.javaops.startup.app.oauth2.extractor.BaseOAuth2UserExtractor;
import ru.javaops.startup.common.error.ErrorType;
import ru.javaops.startup.common.util.Util;
import ru.javaops.startup.user.model.Role;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.service.UserService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthUserService extends DefaultOAuth2UserService {
    private final UserService service;
    private final Map<String, BaseOAuth2UserExtractor> oAuth2UserExtractors;

    @Override
    public AuthUser loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
        BaseOAuth2UserExtractor userExtractor =
                Util.notNull(oAuth2UserExtractors.get(clientRegistrationId), "Unknown provider: " + clientRegistrationId, ErrorType.AUTH_ERROR);
        userExtractor.setOAuth2User(oAuth2User);
        String email = Util.notNull(userExtractor.getEmail(), clientRegistrationId + " account does not contain email", ErrorType.AUTH_ERROR);
        // get or create
        final String firstName = userExtractor.getFirstName();
        return new AuthUser(oAuth2User, service.oauth2Login(email,
                () -> new User(null, email, firstName != null ? firstName : email, userExtractor.getLastName(), userExtractor.getAvatarUrl(), Role.USER)));
    }
}
