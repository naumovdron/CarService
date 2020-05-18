package main.service;

import main.entity.Service;
import main.exception.ServiceNotFoundException;
import main.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Service> listServices() {
        return (List<Service>) serviceRepository.findAll();
    }

    @Override
    public Service findService(int id) {
        Optional<Service> optionalService = serviceRepository.findById(id);
        if (optionalService.isPresent()) {
            return optionalService.get();
        } else {
            throw new ServiceNotFoundException("Service not found");
        }
    }

    @Override
    public Service addService(Service service) {
        return serviceRepository.save(service);
    }
}
