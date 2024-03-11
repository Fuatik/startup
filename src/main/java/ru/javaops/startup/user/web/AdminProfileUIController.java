package ru.javaops.startup.user.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javaops.startup.user.to.ProfileTo;

@Controller
@RequestMapping(AdminProfileUIController.UI_PROFILE)
public class AdminProfileUIController extends AbstractProfileController {
    static final String UI_PROFILE = "/ui/admin/profile";

    @GetMapping
    public String get(@RequestParam String email, ModelMap model) {
        return getByEmail(email, model);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String email) {
        deleteByEmail(email);
        return "redirect:/view/util/closeWindow";
    }

    @PostMapping
    public String update(@Valid ProfileTo profile, BindingResult result, RedirectAttributes redirectAttrs, ModelMap model) {
        return super.update(profile, result, redirectAttrs, model, profile.getEmail(), UI_PROFILE + "?email=" + profile.getEmail());
    }
}
