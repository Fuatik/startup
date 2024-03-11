package ru.javaops.startup.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.startup.AbstractControllerTest;
import ru.javaops.startup.common.util.JsonUtil;
import ru.javaops.startup.user.UsersUtil;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.repository.UserRepository;
import ru.javaops.startup.user.to.UserTo;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.startup.common.util.JsonUtil.writeValue;
import static ru.javaops.startup.user.UserTestData.*;
import static ru.javaops.startup.user.web.PartnerController.REST_URL;

class PartnerControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = PartnerController.REST_URL + '/';

    @Autowired
    private UserRepository repository;

    @Test
    @WithUserDetails(value = "partner")
    void getTo() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(UsersUtil.toTo(admin)));
    }

    @Test
    @WithUserDetails(value = "partner")
    void register() throws Exception {
        UserTo newTo = getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newTo.setId(newId);
        User newUser = UsersUtil.toEntity(newTo);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.getExisted(newId), newUser);
    }

    @Test
    @WithUserDetails(value = "partner")
    void update() throws Exception {
        UserTo updatedTo = getUpdatedTo();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(repository.getExisted(USER_ID), UsersUtil.toEntity(updatedTo));
    }
}