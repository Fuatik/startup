package ru.javaops.startup.ref;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.startup.common.BaseRepository;
import ru.javaops.startup.common.error.NotFoundException;
import ru.javaops.startup.ref.model.RefEntity;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RefRepository extends BaseRepository<RefEntity> {
    @Query("SELECT r FROM RefEntity r WHERE r.code = :code")
    Optional<RefEntity> get(String code);

    @Query("SELECT r FROM RefEntity r ORDER BY r.type, r.order")
    List<RefEntity> getAll();

    @Query("SELECT r FROM RefEntity r WHERE r.type = :type ORDER BY r.order")
    List<RefEntity> getByType(String type);

    default RefEntity getExisted(String code) {
        return get(code).orElseThrow(() -> new NotFoundException("Refs with code=" + code + " not found"));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "ref", key = "#ref.code"),
            @CacheEvict(cacheNames = "refs", key = "#ref.type")
    })
    default void evictCache(RefEntity ref) {
    }
}
