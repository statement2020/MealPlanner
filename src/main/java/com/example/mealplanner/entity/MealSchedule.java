package com.example.mealplanner.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MealSchedule {

    @Id
    private Long id;
    private String mealTime;
    private String dayOfWeek;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
