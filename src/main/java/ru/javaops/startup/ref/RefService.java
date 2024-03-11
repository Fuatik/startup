package ru.javaops.startup.ref;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.startup.common.BaseService;
import ru.javaops.startup.ref.model.RefEntity;
import ru.javaops.startup.ref.to.RefTo;

import java.util.List;

@Service
@Slf4j
public class RefService extends BaseService<RefEntity, RefTo, RefRepository, RefMapper> {

    public RefService(RefRepository repository, RefMapper mapper) {
        super(repository, mapper);
    }

    @Cacheable(cacheNames = "ref")
    public RefTo get(String code) {
        log.debug("get {}", code);
        return toTo(repository.getExisted(code));
    }

    @Cacheable(cacheNames = "refs")
    public List<RefTo> getByType(String type) {
        log.debug("getByType {}", type);
        return toToList(repository.getByType(type));
    }

    @Transactional
    public void delete(String code) {
        log.debug("delete {}", code);
        RefEntity ref = repository.getExisted(code);
        repository.delete(ref);
        repository.evictCache(ref);
    }

    @Transactional
    public void update(RefTo refTo) {
        log.debug("update {}", refTo);
        RefEntity ref = repository.getExisted(refTo.getCode());
        updateFromTo(refTo, ref);
        repository.evictCache(ref);
    }

    public RefEntity create(RefTo refTo) {
        log.debug("create {}", refTo);
        RefEntity ref = createFromTo(refTo);
        repository.evictCache(ref);
        return ref;
    }


    @Transactional
    public void enable(String code, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", code);
        RefEntity ref = repository.getExisted(code);
        ref.setEnabled(enabled);
        repository.evictCache(ref);
    }
}
