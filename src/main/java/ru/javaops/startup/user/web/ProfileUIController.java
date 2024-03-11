package ru.javaops.startup.user.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javaops.startup.app.AuthUtil;
import ru.javaops.startup.app.oauth2.AuthUser;
import ru.javaops.startup.ref.RefService;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.service.ProfileService;
import ru.javaops.startup.user.to.ProfileTo;

import static ru.javaops.startup.app.AuthUtil.authUser;
import static ru.javaops.startup.ref.model.RefType.CONTACT;

@Controller
@RequestMapping(ProfileUIController.UI_PROFILE)
@RequiredArgsConstructor
public class ProfileUIController {
    static final String UI_PROFILE = "/ui/auth/profile";
    static final String VIEW_PROFILE = "auth/profile";

    private final ProfileService service;
    private final RefService refService;

    @GetMapping
    public String get(ModelMap model) {
        return getView(service.getOrCreate(authUser().id()), model);
    }

    @PostMapping("/delete")
    public String delete() {
        service.delete(authUser().id());
        return "redirect:/view/logout";
    }

    @PostMapping
    public String update(@Valid ProfileTo profile, BindingResult result, RedirectAttributes redirectAttrs, ModelMap model) {
        if (result.hasErrors()) {
            return getView(profile, model);
        }
        AuthUser authUser = AuthUtil.get();
        User user = service.update(profile, authUser.id());
        authUser.setUser(user);
        redirectAttrs.addFlashAttribute("saved", true);
        return "redirect:" + UI_PROFILE;
    }

    String getView(ProfileTo profile, ModelMap model) {
        model.addAttribute("profileTo", profile);
        model.addAttribute("contacts", refService.getByType(CONTACT.getCode()));
        return VIEW_PROFILE;
    }
}
