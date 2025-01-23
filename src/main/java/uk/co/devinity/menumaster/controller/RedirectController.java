package uk.co.devinity.menumaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RedirectController {

    @GetMapping
    public ModelAndView redirectWithUsingRedirectPrefix(ModelMap model) {
        return new ModelAndView("redirect:/meal-plans", model);
    }

    @GetMapping("/privacy")
    public String getPrivacyPolicy(Model model) {
        // Set attributes for the Thymeleaf template
        model.addAttribute("effectiveDate", "2025-01-01");
        model.addAttribute("appName", "Menu Master");
        model.addAttribute("businessName", "Devinity Ltd");
        model.addAttribute("contactEmail", "support@menumaster.com");
        model.addAttribute("retentionTimeframe", "30 days");

        return "privacy-policy"; // Name of the Thymeleaf template
    }
}
