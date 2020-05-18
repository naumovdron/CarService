package main.service;

import main.entity.Service;

import java.util.List;

public interface ServiceService {
    List<Service> listServices();
    Service findService(int id);
    Service addService(Service service);
}
