
package uk.co.devinity.menumaster.service;

import uk.co.devinity.menumaster.entity.MealPlan;
import uk.co.devinity.menumaster.entity.User;
import uk.co.devinity.menumaster.repository.MealPlanRepository;
import uk.co.devinity.menumaster.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MealPlanService {

    private static final Logger LOG = LoggerFactory.getLogger(MealPlanService.class);

    private final MealPlanRepository mealPlanRepository;

    private final UserRepository userRepository;

    private final EmailService emailService;

    public MealPlanService(MealPlanRepository mealPlanRepository, UserRepository userRepository, EmailService emailService) {
        this.mealPlanRepository = mealPlanRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public List<MealPlan> getMealPlansForUser(String email) {
        LOG.info("Getting meal plans for email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return mealPlanRepository.findByUser(user);
    }

    public Map<String, List<MealPlan>> getWeeklyMealPlan(String email, LocalDate startOfWeek) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        // Fetch meals for the user within the week range
        List<MealPlan> weeklyMeals = mealPlanRepository.findMealsByUserAndDateBetween(
                user, startOfWeek, startOfWeek.plusDays(6)
        );

        // Group meals by day of the week
        return weeklyMeals.stream()
                .sorted(Comparator.comparing(MealPlan::getDate).thenComparing(MealPlan::getOrderOfDay))
                .collect(Collectors.groupingBy(
                        mealPlan -> mealPlan.getDate().getDayOfWeek().toString(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    public void addMealPlan(MealPlan mealPlan, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        mealPlan.setUser(user);
        mealPlanRepository.save(mealPlan);
    }

    public void updateMealPlan(Long id, MealPlan updatedMealPlan) {
        MealPlan mealPlan = mealPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal plan not found"));
        mealPlan.setMealName(updatedMealPlan.getMealName());
        mealPlan.setCalories(updatedMealPlan.getCalories());
        mealPlan.setProtein(updatedMealPlan.getProtein());
        mealPlan.setCarbs(updatedMealPlan.getCarbs());
        mealPlan.setFats(updatedMealPlan.getFats());
        mealPlanRepository.save(mealPlan);
    }

    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }

    public void inviteUser(String email, String inviterEmail) {
        String subject = "You are invited to join a meal plan!";
        String message = String.format("%s has invited you to join their meal plan. Please login to view and edit it.", inviterEmail);
        emailService.sendEmail(email, subject, message);
    }
}
