package main.service;

import main.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAll();
    Car get(long id);
    void save(Car car);
    void delete(long id);
}
