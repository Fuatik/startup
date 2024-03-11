package ru.javaops.startup.user.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.startup.app.config.SecurityConfig;
import ru.javaops.startup.user.mapper.UserMapper;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.to.UserTo;

import java.net.URI;

import static ru.javaops.startup.common.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.startup.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = PartnerController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class PartnerController extends AbstractUserController {
    static final String REST_URL = SecurityConfig.API_PATH + "/partner/users";

    private final UserMapper mapper;

    @GetMapping("/{id}")
    public UserTo getTo(@PathVariable int id) {
        return mapper.toTo(super.get(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = repository.save(mapper.toEntity(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable int id) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        repository.save(mapper.updateFromTo(userTo, repository.getExisted(id)));
    }
}