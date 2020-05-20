package main.service;

import main.entity.Service;

import java.util.List;

public interface ServiceService {
    List<Service> getAll();
    Service get(long id);
    void save(Service service);
    void delete(long id);
}
