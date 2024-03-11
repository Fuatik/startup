package ru.javaops.startup.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.startup.common.HasId;

import java.net.URI;

@UtilityClass
public class WebUtil {
    // create ResponseEntity
    public static <T extends HasId> ResponseEntity<T> createdResponse(String url, T created) {
        return createdResponse(url + "/{id}", created, created.getId());
    }

    public static <T extends HasId> ResponseEntity<T> createdResponse(String url, T created, Object... params) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url).buildAndExpand(params).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
