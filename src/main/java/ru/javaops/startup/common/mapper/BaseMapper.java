package ru.javaops.startup.common.mapper;

import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

public interface BaseMapper<E, T> {

    E toEntity(T to);

    List<E> toEntityList(Collection<T> tos);

    E updateFromTo(T to, @MappingTarget E entity);

    T toTo(E entity);

    List<T> toToList(Collection<E> entities);
}
