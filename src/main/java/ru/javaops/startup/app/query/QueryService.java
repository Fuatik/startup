package ru.javaops.startup.app.query;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.javaops.startup.app.config.AppConfig;
import ru.javaops.startup.app.query.JdbcRepository.SqlResult;
import ru.javaops.startup.common.error.AppException;
import ru.javaops.startup.common.error.ErrorType;
import ru.javaops.startup.common.util.Util;

import java.util.Map;
import java.util.function.Function;

import static ru.javaops.startup.app.error.BasicExceptionHandler.ERR_PFX;

@Service
@Slf4j
@AllArgsConstructor
public class QueryService {

    private final JdbcRepository jdbcRepository;

    SqlResult execute(String query, Map<String, ?> params, String sort) {
        return execute(query, sql -> {
            if (sort != null) {
                sql += " " + sort;
            }
            return jdbcRepository.execute(sql, params);
        });
    }

    public int update(String query, Map<String, ?> params) {
        return execute(query, sql -> jdbcRepository.update(sql, params));
    }

    private <T> T execute(String query, Function<String, T> sqlExecutor) {
        log.info("execute {}", query);
        String sql = Util.notNull(AppConfig.queriesAdmin.getProperty(query),
                "Query '" + query + "' is not found", ErrorType.BAD_REQUEST);
        try {
            return sqlExecutor.apply(sql);
        } catch (Exception e) {
            log.error(ERR_PFX + "Sql '{}' execution exception:\n {}", sql, e.toString());
            throw new AppException("Sql execution error", ErrorType.APP_ERROR);
        }
    }
}