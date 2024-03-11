package ru.javaops.startup.ref;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.javaops.startup.common.mapper.BaseMapper;
import ru.javaops.startup.common.mapper.MapStructConfig;
import ru.javaops.startup.ref.model.RefEntity;
import ru.javaops.startup.ref.to.RefTo;

@Mapper(config = MapStructConfig.class)
public interface RefMapper extends BaseMapper<RefEntity, RefTo> {
    @Override

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "id", ignore = true)
    RefEntity updateFromTo(RefTo to, @MappingTarget RefEntity entity);
}
