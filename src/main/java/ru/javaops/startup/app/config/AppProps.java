package ru.javaops.startup.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

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
}
