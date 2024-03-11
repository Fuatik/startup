package ru.javaops.startup.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.javaops.startup.app.query.JdbcRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {
    private final JdbcRepository jdbcRepository;

    @Scheduled(cron = "0 5 1 * * *")  // start at 01.05 every day
    public synchronized void backup() {
        jdbcRepository.backup();
    }
}