package ru.javaops.startup.user.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.startup.common.BaseRepository;
import ru.javaops.startup.user.model.UserData;

@Transactional(readOnly = true)
public interface UserDataRepository extends BaseRepository<UserData> {
    default UserData getOrCreate(int userId) {
        return findById(userId).orElseGet(() -> new UserData(userId));
    }
}
