package ru.javaops.startup.user.service;

import org.springframework.stereotype.Service;
import ru.javaops.startup.common.BaseService;
import ru.javaops.startup.user.mapper.UserMapper;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.repository.UserRepository;
import ru.javaops.startup.user.to.UserTo;

@Service
public class UserService extends BaseService<User, UserTo, UserRepository, UserMapper> {
    public UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }
}
