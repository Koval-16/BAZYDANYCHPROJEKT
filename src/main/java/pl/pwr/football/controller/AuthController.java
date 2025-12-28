package pl.pwr.football.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Szuka pliku login.html w templates
    }

    @GetMapping("/admin")
    public String adminHub() { return "/admin"; }

    @GetMapping("/trener")
    public String trenerHub() { return "/trener"; }

    @GetMapping("/sedzia")
    public String sedziaHub() { return "/sedzia"; }

    @GetMapping("/pilkarz")
    public String pilkarzHub() { return "/pilkarz"; }

    @GetMapping("/hub")
    public String defaultHub(Authentication authentication) {
        // Pobieramy role
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_Administrator")) {
            return "redirect:/admin";
        } else if (roles.contains("ROLE_Trener")) {
            return "redirect:/trener";
        } else if (roles.contains("ROLE_Sedzia")) {
            return "redirect:/sedzia";
        } else if (roles.contains("ROLE_Pilkarz")) {
            return "redirect:/pilkarz";
        }

        return "redirect:/"; // Fallback
    }
}