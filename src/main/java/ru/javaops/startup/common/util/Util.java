package ru.javaops.startup.common.util;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.lang.NonNull;
import ru.javaops.startup.common.error.AppException;
import ru.javaops.startup.common.error.ErrorType;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@UtilityClass
public class Util {
    // create ResponseEntity
    public static Properties loadProps(String file) {
        Path path = Paths.get(file);
        try (Reader reader = Files.newBufferedReader(path)) {
            Properties props = new Properties();
            props.load(reader);
            return props;
        } catch (IOException e) {
            throw new IllegalStateException(path.toAbsolutePath() + " load exception", e);
        }
    }

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