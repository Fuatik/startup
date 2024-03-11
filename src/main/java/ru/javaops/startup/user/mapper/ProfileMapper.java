package ru.javaops.startup.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.StringUtils;
import ru.javaops.startup.common.mapper.MapStructConfig;
import ru.javaops.startup.user.model.Contact;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.model.UserData;
import ru.javaops.startup.user.to.ProfileTo;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapStructConfig.class)
public interface ProfileMapper {

    ProfileTo toTo(User user, UserData userData);

    default Map<String, String> toContactsMap(Set<Contact> contacts) {
        return contacts.stream()
                .collect(Collectors.toMap(Contact::getCode, Contact::getValue));
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateUserFromTo(ProfileTo to, @MappingTarget User entity);

    default void updateUserDataFromTo(ProfileTo to, UserData userData) {
        Set<Contact> contacts = userData.getContacts();
        //    https://stackoverflow.com/a/8835704/548473
        contacts.clear();
        contacts.addAll(to.getContacts().entrySet().stream()
                .filter(e -> StringUtils.hasText(e.getValue()))
                .map(e -> new Contact(userData, userData.getUserId(), e.getKey(), e.getValue()))
                .collect(Collectors.toSet()));
    }
}
