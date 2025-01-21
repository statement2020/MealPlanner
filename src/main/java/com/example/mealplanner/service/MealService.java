package com.example.mealplanner.service;

import com.example.mealplanner.entity.Meal;
import com.example.mealplanner.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    public Meal findById(Long id) {
        return mealRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal not found"));
    }

    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    public void update(Long id, Meal updatedMeal) {
        Meal meal = findById(id);
        meal.setTitle(updatedMeal.getTitle());
        meal.setName(updatedMeal.getName());
        meal.setMacros(updatedMeal.getMacros());
        mealRepository.save(meal);
    }

    public void deletMeal(Long id) {
        mealRepository.deleteById(id);
    }
}
