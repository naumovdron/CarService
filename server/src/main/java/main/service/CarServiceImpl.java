package main.service;

import main.entity.Car;
import main.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getAll() {
        return (List<Car>) carRepository.findAll();
    }

    @Override
    public Car get(long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    @Override
    public void update(Car car, long id) {
        if (carRepository.findById(id).isPresent()) {
            car.setId(id);
            carRepository.save(car);
        } else {
            throw new EntityNotFoundException("Car not found");
        }
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public void delete(long id) {
        carRepository.deleteById(id);
    }
}
