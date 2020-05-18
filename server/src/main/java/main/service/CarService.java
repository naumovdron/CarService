package main.service;

import main.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> listCars();
    Car findCar(int id);
    Car addCar(Car car);
}
