package ru.javaops.startup.user.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javaops.startup.user.to.ProfileTo;

import static ru.javaops.startup.app.AuthUtil.authUser;

@Controller
@RequestMapping(ProfileUIController.UI_PROFILE)
public class ProfileUIController extends AbstractProfileController {
    static final String UI_PROFILE = "/ui/auth/profile";

    @GetMapping
    public String get(ModelMap model) {
        return getByEmail(authUser().getEmail(), model);
    }

    @PostMapping("/delete")
    public String delete() {
        deleteByEmail(authUser().getEmail());
        return "redirect:/view/logout";
    }

    @PostMapping
    public String update(@Valid ProfileTo profile, BindingResult result, RedirectAttributes redirectAttrs, ModelMap model) {
        return super.update(profile, result, redirectAttrs, model, authUser().getEmail(), UI_PROFILE);
    }
}
