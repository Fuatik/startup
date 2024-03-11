package ru.javaops.startup.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.Nullable;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import ru.javaops.startup.app.AuthUtil;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

@EnableAutoConfiguration
@Configuration
@RequiredArgsConstructor
// http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-spring-mvc-auto-configuration
public class MvcConfig implements WebMvcConfigurer {
    private final AppProps appProps;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:./resources/static/");
        registry.setOrder(LOWEST_PRECEDENCE);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    //  http://www.codejava.net/frameworks/spring/spring-mvc-url-based-view-resolution-with-urlfilenameviewcontroller-example
    public SimpleUrlHandlerMapping getUrlHandlerMapping() {
        return new SimpleUrlHandlerMapping() {{
            setMappings(new Properties() {{
                put("/view/**", new UrlFilenameViewController());
            }});
            setInterceptors(localeChangeInterceptor(), authInterceptor());
        }};
    }

    //    https://www.baeldung.com/spring-boot-internationalization
    @Bean
    LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    MessageSource messageSource() {
        return new ReloadableResourceBundleMessageSource() {{
            setCacheSeconds((int) appProps.getUpdateCache().toSeconds());
            setDefaultEncoding(StandardCharsets.UTF_8.name());
            setFallbackToSystemLocale(false);
            setBasenames("file:./resources/messages/app", "file:./resources/messages/refs", "file:./resources/messages/err");
        }};
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor() {{
            setParamName("lang");
        }};
    }

    @Bean
    HandlerInterceptor authInterceptor() {
        return new WebRequestHandlerInterceptorAdapter(new WebRequestInterceptor() {
            @Override
            public void postHandle(WebRequest request, ModelMap model) {
                if (model != null) {
                    DefaultSavedRequest savedRequest = (DefaultSavedRequest) request.getAttribute("SPRING_SECURITY_SAVED_REQUEST", SCOPE_SESSION);
                    if (savedRequest != null) {
                        model.addAttribute("adminLogin", savedRequest.getRequestURI().startsWith("/view/admin/queries"));
                    }
                    model.addAttribute("authUser", AuthUtil.safeGet());
                    model.addAttribute("appUser", AuthUtil.safeGetAppUser());
                }
            }

            @Override
            public void preHandle(WebRequest request) {
            }

            @Override
            public void afterCompletion(WebRequest request, @Nullable Exception ex) {
            }
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).excludePathPatterns("/api/**", "/static/**");
        registry.addInterceptor(authInterceptor()).excludePathPatterns("/api/**", "/static/**");
    }
}
