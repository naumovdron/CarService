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
        if (service == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        serviceService.save(service);
        return new ResponseEntity<>(service, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Service> deleteService(@PathVariable("id") long id) {
        Service service = serviceService.get(id);
        if (service == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        serviceService.delete(id);
        return new ResponseEntity<>(service, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@RequestBody @Valid Service service) {
        if (service == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        serviceService.save(service);
        return new ResponseEntity<>(service, HttpStatus.OK);
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
