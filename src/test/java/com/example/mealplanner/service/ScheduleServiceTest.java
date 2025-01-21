package com.example.mealplanner.service;

import com.example.mealplanner.entity.MealSchedule;
import com.example.mealplanner.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private MealSchedule schedule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        schedule = new MealSchedule();
        schedule.setId(1L);
        schedule.setMealTime("08:00 AM");
        schedule.setDayOfWeek("Monday");
    }

    @Test
    void testAddSchedule() {
        when(scheduleRepository.save(any(MealSchedule.class))).thenReturn(schedule);

        MealSchedule savedSchedule = scheduleService.addSchedule(schedule);

        assertNotNull(savedSchedule);
        assertEquals("08:00 AM", savedSchedule.getMealTime());
        verify(scheduleRepository, times(1)).save(any(MealSchedule.class));
    }

    @Test
    void testGetSchedule() {
        when(scheduleRepository.findById(1L)).thenReturn(java.util.Optional.of(schedule));

        MealSchedule foundSchedule = scheduleService.getScheduleById(1L);

        assertNotNull(foundSchedule);
        assertEquals("Monday", foundSchedule.getDayOfWeek());
    }

    @Test
    void testDeleteSchedule() {
        scheduleService.deleteSchedule(1L);

        verify(scheduleRepository, times(1)).deleteById(1L);
    }
}
