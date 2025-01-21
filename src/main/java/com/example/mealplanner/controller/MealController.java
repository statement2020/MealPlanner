package com.example.mealplanner.controller;

import com.example.mealplanner.entity.Meal;
import com.example.mealplanner.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public String listMeals(Model model) {
        List<Meal> meals = mealService.findAll();
        model.addAttribute("meals", meals);
        return "meals/listMeals";
    }

    @GetMapping("/add")
    public String addMealForm(Model model) {
        model.addAttribute("meal", new Meal());
        return "meals/addMeal";
    }

    @PostMapping("/add")
    public String addMeal(@ModelAttribute Meal meal) {
        mealService.save(meal);
        return "redirect:/meals";
    }

    @GetMapping("/edit/{id}")
    public String editMealForm(@PathVariable Long id, Model model) {
        Meal meal = mealService.findById(id);
        model.addAttribute("meal", meal);
        return "meals/editMeal";
    }

    @PostMapping("/edit/{id}")
    public String editMeal(@PathVariable Long id, @ModelAttribute Meal meal) {
        mealService.update(id, meal);
        return "redirect:/meals";
    }
}
