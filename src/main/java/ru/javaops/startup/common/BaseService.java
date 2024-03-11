package ru.javaops.startup.common;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.startup.common.mapper.BaseMapper;
import ru.javaops.startup.common.model.TimestampEntity;
import ru.javaops.startup.common.to.BaseTo;
import ru.javaops.startup.common.validation.ValidationUtil;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BaseService<E extends HasId, T extends BaseTo, R extends BaseRepository<E>, M extends BaseMapper<E, T>> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public BaseService(R repository, M mapper) {
        this(repository, mapper, null, null);
    }

    public BaseService(R repository, M mapper,
                       Function<E, E> prepareForSave, BiFunction<E, E, E> prepareForUpdate) {
        this.repository = repository;
        this.mapper = mapper;
        this.prepareForSave = prepareForSave;
        this.prepareForUpdate = prepareForUpdate;
    }

    @Getter
    protected final R repository;
    @Getter
    protected final M mapper;
    private final Function<E, E> prepareForSave;
    private final BiFunction<E, E, E> prepareForUpdate;

    public T getTo(int id) {
        log.info("getTo by id={}", id);
        return toTo(repository.getExisted(id));
    }

    public E get(int id) {
        log.info("get by id={}", id);
        return repository.getExisted(id);
    }

    public List<E> getAll() {
        return getAll(Sort.unsorted());
    }

    public List<E> getAll(Sort sort) {
        log.info("getAll");
        return repository.findAll(sort);
    }

    public List<T> getAllTos() {
        return getAllTos(Sort.unsorted());
    }

    public List<T> getAllTos(Sort sort) {
        log.info("getAllTos");
        return toToList(repository.findAll(sort));
    }

    public E createFromTo(T to) {
        log.info("createFromTo {}", to);
        ValidationUtil.checkNew(to);
        E entity = toEntity(to);
        if (prepareForSave != null) entity = prepareForSave.apply(entity);
        return repository.save(entity);
    }

    public E create(E entity) {
        log.info("create {}", entity);
        ValidationUtil.checkNew(entity);
        if (prepareForSave != null) entity = prepareForSave.apply(entity);
        return repository.save(entity);
    }

    public void delete(int id) {
        log.info("delete by id={}", id);
        repository.deleteExisted(id);
    }

    @Transactional
    public E update(E entity, int id) {
        log.info("update {} with id={}", entity, id);
        ValidationUtil.assureIdConsistent(entity, id);
        if (prepareForUpdate != null) {
            E dbEntity = repository.getExisted(entity.id());
            entity = prepareForUpdate.apply(entity, dbEntity);
        }
        return repository.save(entity);
    }

    @Transactional
    public E updateFromTo(T to, int id) {
        log.info("updateFromTo {} with id={}", to, id);
        ValidationUtil.assureIdConsistent(to, id);
        E dbEntity = repository.getExisted(to.id());
        return repository.save(updateFromTo(to, dbEntity));
    }

    // delegate to mapper
    public E toEntity(T to) {
        return mapper.toEntity(to);
    }

    public List<E> toEntityList(Collection<T> tos) {
        return mapper.toEntityList(tos);
    }

    public E updateFromTo(T to, E entity) {
        return mapper.updateFromTo(to, entity);
    }

    public T toTo(E entity) {
        return mapper.toTo(entity);
    }

    public List<T> toToList(List<E> entities) {
        return mapper.toToList(entities);
    }

    @Transactional
    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        E entity = repository.getExisted(id);
        if (entity instanceof TimestampEntity te) {
            te.setEnabled(enabled);
        } else {
            throw new UnsupportedOperationException("enabling for " + entity + " is not supported");
        }
    }
}
