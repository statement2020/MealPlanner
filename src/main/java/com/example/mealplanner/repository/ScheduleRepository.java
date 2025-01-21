package com.example.mealplanner.repository;

import com.example.mealplanner.entity.MealSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<MealSchedule, Long> {
    // Custom query methods can be added here
}
