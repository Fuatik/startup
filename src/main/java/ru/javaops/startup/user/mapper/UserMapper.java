package ru.javaops.startup.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.javaops.startup.common.mapper.BaseMapper;
import ru.javaops.startup.common.mapper.MapStructConfig;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.to.UserTo;

@Mapper(config = MapStructConfig.class)
public interface UserMapper extends BaseMapper<User, UserTo> {

    @Mapping(target = "email", expression = "java(to.getEmail().toLowerCase())")
    @Mapping(target = "roles", expression = "java({Role.USER})")
    @Override
    User toEntity(UserTo to);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(to.getEmail().toLowerCase())")
    @Override
    User updateFromTo(UserTo to, @MappingTarget User entity);
}
