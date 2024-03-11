package ru.javaops.startup.app.query;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
@AllArgsConstructor
public class JdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public int update(String sql, Map<String, ?> params) {
        log.info("Execute update '{}' with params {}", sql, params);
        return jdbcTemplate.update(sql, params);
    }

    public synchronized void backup() {
        execute(String.format("BACKUP TO '%s'", "./db/backup/" + DateTimeFormatter.ISO_DATE.format(LocalDate.now()) + ".zip"));
    }

    public Boolean execute(String sql) {
        log.info("Execute " + sql);
        return jdbcTemplate.execute(sql, PreparedStatement::execute);
    }

    SqlResult execute(String sql, Map<String, ?> params) {
        return jdbcTemplate.query(sql, params, rs -> {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> headers = new ArrayList<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                headers.add(JdbcUtils.lookupColumnName(rsmd, i).toLowerCase());
            }
            List<Object[]> rows = new ArrayList<>();
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = JdbcUtils.getResultSetValue(rs, i);
                }
                rows.add(row);
            }
            return new SqlResult(headers, rows);
        });
    }

    record SqlResult(List<String> headers, List<Object[]> rows) {
    }
}