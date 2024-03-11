package ru.javaops.startup.user.service;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import ru.javaops.startup.common.BaseService;
import ru.javaops.startup.user.mapper.UserMapper;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.repository.UserRepository;
import ru.javaops.startup.user.to.UserTo;

import java.util.function.Supplier;

@Service
public class UserService extends BaseService<User, UserTo, UserRepository, UserMapper> {
    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    public User oauth2Login(String email, Supplier<User> supplier) {
        return repository.findByEmailIgnoreCase(email)
                .map(u -> {
                    if (!u.isEnabled()) {
                        String msg = "User " + email + " disabled";
                        log.warn(msg);
                        throw new OAuth2AuthenticationException(msg);
                    }
                    return u;
                }).orElseGet(() -> repository.save(supplier.get()));
    }
}
