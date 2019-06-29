package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal> > repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        //MealsUtil.MEALS.forEach((Meal it) -> save(it,1));
        List<Meal> meals =  MealsUtil.MEALS;
        for (Meal meal : meals) {
            save(meal, 1);
            save(meal, 2);
        }
    }

    @Override
    public Meal save(Meal meal, int userId) {
        repository.putIfAbsent(userId, new HashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.remove(id) != null;
        }   else {
            return false;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.get(userId);
        } else {
            return null;
        }

    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            List<Meal> result = new ArrayList<>(userMeals.values());
            result.sort(Comparator.comparing(Meal::getDateTime));
            return result;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.values().stream()
                    .filter(it -> DateTimeUtil.isBetweenDate(it.getDate(), startDate, endDate))
                    .sorted(Comparator.comparing(Meal::getDateTime))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}

