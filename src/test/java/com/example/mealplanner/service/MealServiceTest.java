package com.example.mealplanner.service;

import com.example.mealplanner.entity.Meal;
import com.example.mealplanner.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    private Meal meal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        meal = new Meal();
        meal.setId(1L);
        meal.setName("Breakfast");
        meal.setTitle("Healthy start to the day");
    }

    @Test
    void testAddMeal() {
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        Meal savedMeal = mealService.save(meal);

        assertNotNull(savedMeal);
        assertEquals("Breakfast", savedMeal.getName());
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void testGetMeal() {
        when(mealRepository.findById(1L)).thenReturn(java.util.Optional.of(meal));

        Meal foundMeal = mealService.findById(1L);

        assertNotNull(foundMeal);
        assertEquals("Breakfast", foundMeal.getName());
    }

    @Test
    void testDeleteMeal() {
        mealService.deletMeal(1L);

        verify(mealRepository, times(1)).deleteById(1L);
    }
}
