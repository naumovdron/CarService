package main.service;

import main.entity.Service;
import main.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Service> getAll() {
        return (List<Service>) serviceRepository.findAll();
    }

    @Override
    public Service get(long id) {
        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isPresent()) {
            return optionalService.get();
        } else {
            throw new EntityNotFoundException("Service not found");
        }
    }

    @Override
    public void save(Service service) {
        serviceRepository.save(service);
    }

    @Override
    public void delete(long id) {
        serviceRepository.deleteById(id);
    }
}
