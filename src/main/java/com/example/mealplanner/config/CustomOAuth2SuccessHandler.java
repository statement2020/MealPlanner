package com.example.mealplanner.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomOAuth2SuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Fetch user details from the OAuth2User object
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        LOG.info("login for {} with email {}", name, email);
        // Custom logic, e.g., create or update a user in your database
        // userService.saveOrUpdateUser(email, name);

        // Set the default URL to redirect to after login
        setDefaultTargetUrl("/dashboard");

        // Call the parent class's onAuthenticationSuccess to handle the redirection
        super.onAuthenticationSuccess(request, response, authentication);
    }
}