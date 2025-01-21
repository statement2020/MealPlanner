package com.example.mealplanner.service;

import com.example.mealplanner.entity.MealSchedule;
import com.example.mealplanner.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<MealSchedule> findAll() {
        return scheduleRepository.findAll();
    }

    public MealSchedule addSchedule(MealSchedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public MealSchedule getScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}
