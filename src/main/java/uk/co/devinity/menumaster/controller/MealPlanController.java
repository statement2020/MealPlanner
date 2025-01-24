
package uk.co.devinity.menumaster.controller;

import uk.co.devinity.menumaster.entity.MealPlan;
import uk.co.devinity.menumaster.models.ActionRequest;
import uk.co.devinity.menumaster.service.MealPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/meal-plans")
public class MealPlanController {

    private static final Logger LOG = LoggerFactory.getLogger(MealPlanController.class);

    @Autowired
    private MealPlanService mealPlanService;

    @GetMapping
    public String viewMealPlans(Model model, Principal principal) {
        var email = getEmail(principal);
        List<MealPlan> mealPlans = mealPlanService.getMealPlansForUser(email);
        model.addAttribute("mealPlans", mealPlans);
        return "meal-plans";

    }

    @GetMapping("/mealplan")
    public String getWeeklyMealPlan(@RequestParam(value = "date", required = false) String dateParam,
                                    Principal principal,
                                    Model model) {
        var email = getEmail(principal);

        LocalDate selectedDate = (dateParam != null) ? LocalDate.parse(dateParam) : LocalDate.now();
        LocalDate startOfWeek = selectedDate.with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek());

        // Set dates for navigation
        model.addAttribute("currentDate", selectedDate.toString());
        model.addAttribute("previousWeek", startOfWeek.minusWeeks(1).toString());
        model.addAttribute("nextWeek", startOfWeek.plusWeeks(1).toString());
        LOG.info(model.toString());
        model.addAttribute("weeklyMealPlan", mealPlanService.getWeeklyMealPlan(email, startOfWeek));

        return "mealplan";
    }

    private String getEmail(final Principal principal) {
        var user = (OAuth2AuthenticationToken) principal;
        return user.getPrincipal().getAttribute("email");
    }

    @PostMapping
    public String addMealPlan(@ModelAttribute MealPlan mealPlan, Principal principal) {
        mealPlanService.addMealPlan(mealPlan, getEmail(principal));
        return "redirect:/meal-plans";
    }

    @PostMapping("/invite")
    public String inviteUser(@RequestParam String email, Principal principal) {
        mealPlanService.inviteUser(email, getEmail(principal));
        return "redirect:/meal-plans";
    }

    @PutMapping("/{id}")
    public String updateMealPlan(@PathVariable Long id, @ModelAttribute MealPlan mealPlan) {
        mealPlanService.updateMealPlan(id, mealPlan);
        return "redirect:/meal-plans";
    }

    @PostMapping("/{id}")
    public String deleteMealPlan(@PathVariable Long id, @ModelAttribute ActionRequest body) {
        if (body._method().equals("delete")) {
            mealPlanService.deleteMealPlan(id);
        }
        return "redirect:/meal-plans";
    }
}
