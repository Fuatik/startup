package ru.javaops.startup.ref.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javaops.startup.app.config.SecurityConfig;
import ru.javaops.startup.ref.RefService;
import ru.javaops.startup.ref.model.RefEntity;
import ru.javaops.startup.ref.to.RefTo;

import java.util.List;

import static ru.javaops.startup.common.util.WebUtil.createdResponse;
import static ru.javaops.startup.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RefController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j

public class RefController {
    static final String REST_URL = SecurityConfig.API_PATH + "/admin/refs";
    private RefService service;

    @GetMapping("/{code}")
    public RefTo get(@PathVariable String code) {
        return service.get(code);
    }

    @GetMapping("/by-type")
    public List<RefTo> getByType(@RequestParam String type) {
        return service.getByType(type);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        service.delete(code);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefEntity> create(@Valid @RequestBody RefTo refTo) {
        checkNew(refTo);
        RefEntity created = service.create(refTo);
        return createdResponse(REST_URL + "/{code}", created, created.getCode());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RefTo refTo) {
        service.update(refTo);
    }

    @PatchMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable String code, @RequestParam boolean enabled) {
        service.enable(code, enabled);
    }
}
