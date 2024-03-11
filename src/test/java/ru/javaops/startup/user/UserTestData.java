package ru.javaops.startup.user;

import ru.javaops.startup.MatcherFactory;
import ru.javaops.startup.user.model.Role;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.to.UserTo;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered");
    public static final MatcherFactory.Matcher<UserTo> USER_TO_MATCHER = MatcherFactory.usingEqualsComparator(UserTo.class);

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "UserLastName", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "AdminLastName", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "GuestLastName");

    public static UserTo getNewTo() {
        return new UserTo(null, "New", "new@gmail.com", "NewLastName");
    }

    public static UserTo getUpdatedTo() {
        return new UserTo(USER_ID, "UpdatedName", USER_MAIL, "UpdatedLastName");
    }

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "NewLastName", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "UpdatedLastName", false, new Date(), Collections.singleton(Role.ADMIN));
    }
}
