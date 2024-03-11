package ru.javaops.startup.common.util;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.lang.NonNull;
import ru.javaops.startup.common.error.AppException;
import ru.javaops.startup.common.error.ErrorType;

@UtilityClass
public class Util {
    @NonNull
    public static <T> T notNull(T value, String msg) {
        return notNull(value, msg, ErrorType.APP_ERROR);
    }

    @NonNull
    public static <T> T notNull(T value, String msg, ErrorType type) {
        if (value == null) {
            throw new AppException(msg, type);
        }
        return value;
    }

    public static Class<?> getEffectiveClass(Object o) {
        return o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    }
}