package ru.javaops.startup.app;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javaops.startup.app.oauth2.AuthUser;
import ru.javaops.startup.common.error.ErrorType;
import ru.javaops.startup.common.util.Util;
import ru.javaops.startup.user.model.User;

public class AuthUtil {
    public static AppUser safeGetAppUser() {
        return (getPrincipal() instanceof AppUser u) ? u : null;
    }

    public static AppUser getAppUser() {
        return Util.notNull(safeGetAppUser(), "No appUser found", ErrorType.AUTH_ERROR);
    }

    public static AuthUser safeGet() {
        return (getPrincipal() instanceof AuthUser au) ? au : null;
    }

    public static AuthUser get() {
        return Util.notNull(safeGet(), "No authorized user found", ErrorType.AUTH_ERROR);
    }

    public static User authUser() {
        return get().getUser();
    }

    private static Object getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        return auth.getPrincipal();
    }
}
