package ru.javaops.startup.ref.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.startup.AbstractControllerTest;
import ru.javaops.startup.ref.RefMapper;
import ru.javaops.startup.ref.RefRepository;
import ru.javaops.startup.ref.model.RefEntity;
import ru.javaops.startup.ref.model.RefType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.startup.common.util.JsonUtil.writeValue;
import static ru.javaops.startup.ref.RefTestData.*;


public class RefControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RefController.REST_URL + "/";

    @Autowired
    private RefRepository repository;
    @Autowired
    private RefMapper mapper;

    @Test
    @WithUserDetails(value = "admin")
    void getByCode() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + TG_CODE))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(REF_TO_MATCHER.contentJson(mapper.toTo(refTg)));
    }

    @Test
    @WithUserDetails(value = "admin")
    void getByType() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by-type?type=" + RefType.CONTACT.getCode()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(REF_TO_MATCHER.contentJson(mapper.toToList(List.of(refTg, refWa, refPh, refSk))));
    }

    @Test
    @WithUserDetails(value = "admin")
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + TG_CODE))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.get(TG_CODE).isPresent());
    }

    @Test
    @WithUserDetails(value = "admin")
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(RefController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(mapper.toTo(getUpdated()))))
                .andDo(print())
                .andExpect(status().isNoContent());
        RefEntity existed = repository.getExisted(TG_CODE);
        REF_MATCHER.assertMatch(repository.getExisted(TG_CODE), getUpdated());
    }

    @Test
    @WithUserDetails(value = "admin")
    void create() throws Exception {
        RefEntity newRef = getNew();
        perform(MockMvcRequestBuilders.post(RefController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(mapper.toTo(newRef))))
                .andExpect(status().isCreated())
                .andExpect(REF_MATCHER.contentJson(newRef));
        REF_MATCHER.assertMatch(repository.getExisted(newRef.getCode()), newRef);
    }

    @Test
    @WithUserDetails(value = "admin")
    void createNoBody() throws Exception {
        perform(MockMvcRequestBuilders.post(RefController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = "admin")
    void enable() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + TG_CODE)
                .param("enabled", "false")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.getExisted(TG_CODE).isEnabled());

        perform(MockMvcRequestBuilders.patch(REST_URL + TG_CODE)
                .param("enabled", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(repository.getExisted(TG_CODE).isEnabled());
    }
}
