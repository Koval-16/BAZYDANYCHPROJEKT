package pl.pwr.football.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwr.football.dto.UserRegisterDto;
import pl.pwr.football.service.UserService;

@Controller
@RequestMapping("/admin/uzytkownicy")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/nowy")
    public String showAddUserForm(Model model) {
        model.addAttribute("userDto", new UserRegisterDto());
        return "add-user";
    }

    @PostMapping("/nowy")
    public String addUser(@Valid @ModelAttribute("userDto") UserRegisterDto userDto,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "add-user";
        }

        try {
            userService.registerUser(userDto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-user";
        }

        return "redirect:/hub?successUser";
    }
}
