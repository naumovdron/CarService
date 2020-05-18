package main.service;

import main.entity.Car;
import main.exception.CarNotFoundException;
import main.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> listCars() {
        return (List<Car>) carRepository.findAll();
    }

    @Override
    public Car findCar(int id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            return optionalCar.get();
        } else {
            throw new CarNotFoundException("Car not found");
        }
    }

    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }
}
