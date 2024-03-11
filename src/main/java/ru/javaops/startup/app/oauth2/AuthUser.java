package ru.javaops.startup.app.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.javaops.startup.user.model.Role;
import ru.javaops.startup.user.model.User;

import java.util.Collection;
import java.util.Map;

public class AuthUser implements OAuth2User {
    private final OAuth2User oauth2User;
    @Getter
    @Setter
    private User user;

    public AuthUser(@NonNull OAuth2User oauth2User, @NonNull User user) {
        this.oauth2User = oauth2User;
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    public int id() {
        return user.id();
    }

    public String email() {
        return user.getEmail();
    }

    public boolean hasRole(Role role) {
        return user.hasRole(role);
    }

    @Override
    public String toString() {
        return "AuthUser:" + user.getId() + '[' + user.getEmail() + ']';
    }
}
