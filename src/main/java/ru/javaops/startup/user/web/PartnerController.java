package ru.javaops.startup.user.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.startup.app.config.SecurityConfig;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.to.UserTo;

import static ru.javaops.startup.common.util.WebUtil.createdResponse;

@RestController
@RequestMapping(value = PartnerController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class PartnerController extends AbstractUserController {
    static final String REST_URL = SecurityConfig.API_PATH + "/partner/users";

    @GetMapping("/{id}")
    public UserTo getTo(@PathVariable int id) {
        return service.getTo(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = service.createFromTo(userTo);
        return createdResponse(REST_URL, created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable int id) {
        service.updateFromTo(userTo, id);
    }
}