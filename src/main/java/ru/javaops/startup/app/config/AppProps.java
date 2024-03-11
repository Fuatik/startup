package ru.javaops.startup.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import ru.javaops.startup.user.model.Role;

import java.time.Duration;
import java.util.List;

@ConfigurationProperties("app")
@Validated
@Getter
@Setter
public class AppProps {

    /**
     * Host url
     */
    @NonNull
    private String hostUrl;

    /**
     * Interval for dynamic resource update
     */
    @NonNull
    private Duration updateCache;

    /*
      App users for Admin/Partner API
     */
    @NonNull
    private List<User> users;

    public record User(@NonNull String name, @NonNull String password, @NonNull List<Role> roles) {
    }
}
