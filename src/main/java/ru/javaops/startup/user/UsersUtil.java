package ru.javaops.startup.user;

import ru.javaops.startup.user.model.Role;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.to.UserTo;

public class UsersUtil {

    public static User toEntity(UserTo userTo) {
        return new User(userTo.getId(), userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getLastName(), Role.USER);
    }

    public static User updateFromTo(UserTo userTo, User user) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setLastName(userTo.getLastName());
        return user;
    }

    public static UserTo toTo(User user) {
        return new UserTo(user.id(), user.getName(), user.getEmail(), user.getLastName());
    }
}