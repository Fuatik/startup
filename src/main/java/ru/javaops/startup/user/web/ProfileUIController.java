package ru.javaops.startup.user.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.javaops.startup.app.AuthUtil;
import ru.javaops.startup.app.oauth2.AuthUser;
import ru.javaops.startup.user.model.User;
import ru.javaops.startup.user.service.UserService;
import ru.javaops.startup.user.to.UserTo;

import static ru.javaops.startup.app.AuthUtil.authUser;

@Controller
@RequestMapping(ProfileUIController.UI_PROFILE)
@RequiredArgsConstructor
public class ProfileUIController {
    static final String UI_PROFILE = "/ui/auth/profile";
    static final String VIEW_PROFILE = "auth/profile";

    private final UserService userService;

    @GetMapping
    public ModelAndView get() {
        UserTo userTo = userService.getTo(authUser().id());
        return new ModelAndView(VIEW_PROFILE, "userTo", userTo);
    }

    @PostMapping("/delete")
    public String delete() {
        userService.delete(authUser().id());
        return "redirect:/view/logout";
    }

    @PostMapping
    public String update(@Valid UserTo userTo, BindingResult result, RedirectAttributes redirectAttrs, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("userTo", userTo);
            return VIEW_PROFILE;
        }
        AuthUser authUser = AuthUtil.get();
        User user = userService.updateFromTo(userTo, authUser.id());
        authUser.setUser(user);
        redirectAttrs.addFlashAttribute("saved", true);
        return "redirect:" + UI_PROFILE;
    }
}
