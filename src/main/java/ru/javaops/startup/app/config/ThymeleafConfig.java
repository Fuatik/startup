package ru.javaops.startup.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

//http://www.thymeleaf.org/doc/articles/thymeleaf3migration.html
@Configuration
@RequiredArgsConstructor
public class ThymeleafConfig {
    private final AppProps appProps;

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        return new SpringTemplateEngine() {{
            addTemplateResolver(new FileTemplateResolver() {{
                setPrefix("./resources/view/");
                setCacheable(false);
                setSuffix(".html");
                setCharacterEncoding("UTF-8");
                setCacheable(true);
                setCacheTTLMs(appProps.getUpdateCache().toMillis());
            }});
        }};
    }
}
