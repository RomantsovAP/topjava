package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        Boolean result = repository.delete(id, userId);
        if (!result) {
            throw new NotFoundException("");
        }
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        Meal result = repository.get(id, userId);
        if (result == null) {
            throw new NotFoundException("");
        }
        return result;
    }


    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getAll(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.getAll(userId, startDate, endDate);
    }
}