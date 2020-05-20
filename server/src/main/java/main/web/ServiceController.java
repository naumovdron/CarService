package main.web;

import main.entity.Service;
import main.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @PostMapping("")
    public ResponseEntity<Service> saveService(@RequestBody @Valid Service service) {
        try {
            serviceService.save(service);
            return new ResponseEntity<>(service, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Service> deleteService(@PathVariable("id") long id) {
        try {
            Service service = serviceService.get(id);
            serviceService.delete(id);
            return new ResponseEntity<>(service, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@RequestBody @Valid Service service,  @PathVariable("id") long id) {
        try {
            serviceService.update(service, id);
            return new ResponseEntity<>(service, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = serviceService.getAll();
        if (services.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getService(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(serviceService.get(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setCarService(ServiceService serviceService) {
        this.serviceService = serviceService;
    }
}
