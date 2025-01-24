
package uk.co.devinity.menumaster.repository;

import uk.co.devinity.menumaster.entity.MealPlan;
import uk.co.devinity.menumaster.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUser(User user);
    List<MealPlan> findMealsByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}
