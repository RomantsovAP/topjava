package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();

        Map<LocalDate, Integer> totalCaloriesPerDay = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            totalCaloriesPerDay.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), (k, v) -> k + v);
        }
        for (UserMeal userMeal : mealList) {
            if (!TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                continue;
            }
            if (totalCaloriesPerDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                result.add(new UserMealWithExceed(userMeal, true));
            } else {
                result.add(new UserMealWithExceed(userMeal, false));
            }
        }
        return result;
    }
}
