package main.service;

import main.entity.Master;

import java.util.List;

public interface MasterService {
    List<Master> getAll();
    Master get(long id);
    void save(Master master);
    void delete(long id);
}
