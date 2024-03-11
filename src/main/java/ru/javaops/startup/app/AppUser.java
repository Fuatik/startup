package ru.javaops.startup.app;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUser extends User {
    public AppUser(UserDetails user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public String toString() {
        return "AppUser:" + getUsername();
    }
}
