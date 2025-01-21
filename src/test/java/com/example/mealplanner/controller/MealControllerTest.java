package com.example.mealplanner.controller;

import com.example.mealplanner.service.MealService;
import com.example.mealplanner.entity.Meal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MealControllerTest {

    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mealController).build();
    }

    @Test
    void testListMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andExpect(status().isOk())
                .andExpect(view().name("meals/listMeals"));
    }

    @Test
    void testAddMeal() throws Exception {
        Meal meal = new Meal();
        meal.setName("Lunch");
        meal.setTitle("Healthy lunch");

        when(mealService.save(any(Meal.class))).thenReturn(meal);

        mockMvc.perform(post("/meals/add")
                        .param("mealName", "Lunch")
                        .param("mealDescription", "Healthy lunch"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/meals"));
    }
}
