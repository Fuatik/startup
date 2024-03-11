package ru.javaops.startup.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.startup.app.config.AppConfig;
import ru.javaops.startup.app.query.JdbcRepository;

@RestController
@RequestMapping(value = "/api/admin/", produces = MediaType.TEXT_PLAIN_VALUE)
@Slf4j
@RequiredArgsConstructor
public class AdminAppController {

    private final AppConfig appConfig;
    private final JdbcRepository jdbcRepository;

    @PostMapping("refresh")
    String refresh() {
        log.info("refresh form {}", AuthUtil.getAppUser());
        appConfig.init();
        return "OK";
    }

    @PostMapping("/backup")
    public void backup() {
        jdbcRepository.backup();
    }
}