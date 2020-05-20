package main.service;

import main.entity.Work;

import java.util.List;

public interface WorkService {
    List<Work> getAll();
    Work get(long id);
    void update(Work work, long id);
    void save(Work work);
    void delete(long id);
}
