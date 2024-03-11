package ru.javaops.startup.app.query;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javaops.startup.app.query.JdbcRepository.SqlResult;

import java.util.Map;

@Controller
@Slf4j
@AllArgsConstructor
@RequestMapping("/ui/admin")
public class AdminQueryController {
    private final QueryService queryService;

    @GetMapping("/query")
    public ModelAndView query(@RequestParam String query,
                              @RequestParam @Nullable String sort,
                              @RequestParam Map<String, String> params) {
        log.info("execute {}", query);
        SqlResult result = queryService.execute(query, params, sort);
        return new ModelAndView("admin/query", Map.of("result", result, "query", query));
    }
}