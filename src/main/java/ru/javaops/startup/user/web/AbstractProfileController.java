package ru.javaops.startup.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javaops.startup.app.AuthUtil;
import ru.javaops.startup.app.oauth2.AuthUser;
import ru.javaops.startup.ref.RefService;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.service.ProfileService;
import ru.javaops.startup.user.to.ProfileTo;

import static ru.javaops.startup.ref.model.RefType.CONTACT;

@Controller
@Slf4j
public class AbstractProfileController {
    static final String VIEW_PROFILE = "auth/profile";

    @Autowired
    private ProfileService service;
    @Autowired
    private RefService refService;

    protected String getByEmail(String email, ModelMap model) {
        return getView(service.getOrCreate(email), model);
    }

    protected void deleteByEmail(String email) {
        service.delete(email);
    }

    protected String update(ProfileTo profile, BindingResult result, RedirectAttributes redirectAttrs, ModelMap model, String email, String uiProfile) {
        if (result.hasErrors()) {
            return getView(profile, model);
        }
        User user = service.update(profile, email);
        AuthUser authUser = AuthUtil.safeGet();
        if (authUser != null) {
            authUser.setUser(user);
        }
        redirectAttrs.addFlashAttribute("saved", true);
        return "redirect:" + uiProfile;
    }

    private String getView(ProfileTo profile, ModelMap model) {
        model.addAttribute("profileTo", profile);
        model.addAttribute("contacts", refService.getByType(CONTACT.getCode()));
        return VIEW_PROFILE;
    }
}
