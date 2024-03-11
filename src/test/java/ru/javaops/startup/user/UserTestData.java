package ru.javaops.startup.user;

import ru.javaops.startup.MatcherFactory;
import ru.javaops.startup.user.model.Role;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.to.UserTo;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "startpoint");
    public static final MatcherFactory.Matcher<UserTo> USER_TO_MATCHER = MatcherFactory.usingEqualsComparator(UserTo.class);

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";

    public static final User user = new User(USER_ID, USER_MAIL, "User", "UserLastName", Role.USER);
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "Admin", "AdminLastName", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, GUEST_MAIL, "Guest", "GuestLastName");

    public static UserTo getNewTo() {
        return new UserTo(null, "new@gmail.com", "New", "NewLastName");
    }

    public static UserTo getUpdatedTo() {
        return new UserTo(USER_ID, USER_MAIL, "UpdatedName", "UpdatedLastName");
    }

    public static User getNew() {
        return new User(null, "new@gmail.com", "New", "NewLastName", Role.USER);
    }

    public static User getUpdated() {
        return new User(USER_ID, USER_MAIL, "UpdatedName", "UpdatedLastName", Role.ADMIN);
    }
}
