package pl.pwr.football.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Pobieramy role zalogowanego użytkownika
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_Administrator")) {
            response.sendRedirect("/admin");
        } else if (roles.contains("ROLE_Trener")) {
            response.sendRedirect("/trener");
        } else if (roles.contains("ROLE_Sedzia")) {
            response.sendRedirect("/sedzia");
        } else if (roles.contains("ROLE_Pilkarz")) {
            response.sendRedirect("/pilkarz");
        } else {
            response.sendRedirect("/"); // Domyślnie
        }
    }
}