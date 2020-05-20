package main.web;

import main.entity.Car;
import main.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping("")
    public ResponseEntity<Car> saveCar(@RequestBody @Valid Car car) {
        try {
            carService.save(car);
            return new ResponseEntity<>(car, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable("id") long id) {
        try {
            Car car = carService.get(id);
            carService.delete(id);
            return new ResponseEntity<>(car, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@RequestBody @Valid Car car, @PathVariable("id") long id) {
        try {
            carService.update(car, id);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAll();
        if (cars.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(carService.get(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
}
