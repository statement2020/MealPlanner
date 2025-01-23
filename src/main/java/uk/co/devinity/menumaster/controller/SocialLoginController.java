
package uk.co.devinity.menumaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SocialLoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/oauth2/callback")
    public String oauthCallback() {
        return "redirect:/dashboard";
    }
}
