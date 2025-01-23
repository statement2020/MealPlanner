package uk.co.devinity.menumaster.service;

import uk.co.devinity.menumaster.entity.MealPlan;
import uk.co.devinity.menumaster.entity.User;
import uk.co.devinity.menumaster.repository.MealPlanRepository;
import uk.co.devinity.menumaster.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealPlanServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private MealPlanRepository mealPlanRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MealPlanService mealPlanService;

    @Test
    public void testGetMealPlansForUser() {
        User user = new User();
        user.setEmail("test@example.com");

        MealPlan mealPlan = new MealPlan();
        mealPlan.setMealName("Lunch");
        mealPlan.setUser(user);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(mealPlanRepository.findByUser(user)).thenReturn(List.of(mealPlan));

        List<MealPlan> mealPlans = mealPlanService.getMealPlansForUser("test@example.com");

        assertEquals(1, mealPlans.size());
        assertEquals("Lunch", mealPlans.get(0).getMealName());
    }

    @Test
    public void testAddMealPlan() {
        User user = new User();
        user.setEmail("test@example.com");

        MealPlan mealPlan = new MealPlan();
        mealPlan.setMealName("Dinner");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        mealPlanService.addMealPlan(mealPlan, "test@example.com");

        verify(mealPlanRepository, times(1)).save(any(MealPlan.class));
    }

    @Test
    public void testInviteUser() {
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        mealPlanService.inviteUser("invitee@example.com", "inviter@example.com");

        verify(emailService, times(1)).sendEmail(eq("invitee@example.com"), anyString(), anyString());
    }
}
