package ru.javaops.startup.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.startup.user.mapper.ProfileMapper;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.model.UserData;
import ru.javaops.startup.user.repository.UserDataRepository;
import ru.javaops.startup.user.repository.UserRepository;
import ru.javaops.startup.user.to.ProfileTo;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final ProfileMapper mapper;

    @Transactional
    public ProfileTo getOrCreate(int userId) {
        User user = userRepository.getExisted(userId);
        UserData userData = userDataRepository.getOrCreate(userId);
        return mapper.toTo(user, userData);
    }

    @Transactional
    public User update(ProfileTo profileTo, int userId) {
        User user = userRepository.getExisted(userId);
        mapper.updateUserFromTo(profileTo, user);
        UserData userData = userDataRepository.getOrCreate(userId);
        mapper.updateUserDataFromTo(profileTo, userData);
        userDataRepository.save(userData);
        return user;
    }

    @Transactional
    public void delete(int userId) {
        User user = userRepository.getExisted(userId);
        userDataRepository.delete(user.id());
        userRepository.delete(user);
    }
}
