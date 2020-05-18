package main.web;

import main.entity.Car;
import main.exception.CarNotFoundException;
import main.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/carService")
public class CarServiceController {
    private CarService carService;
    private MasterService masterService;
    private ServiceService serviceService;
    private WorkService workService;

    @PostMapping(value = "/addCar", consumes = "car/json", produces = "car/json")
    public Car addCar(@RequestBody Car newCar) {
        return carService.addCar(newCar);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllApplications() {
        List<Car> list = carService.listCars();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getApplication(@PathVariable("id") int id) {
        try {
            return new ResponseEntity<>(carService.findCar(id), HttpStatus.OK);
        } catch (CarNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    @Autowired
    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }

    @Autowired
    public void setServiceService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Autowired
    public void setWorkService(WorkService workService) {
        this.workService = workService;
    }
}
