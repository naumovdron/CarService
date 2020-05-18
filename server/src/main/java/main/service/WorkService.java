package main.service;

import main.entity.Work;

import java.util.List;

public interface WorkService {
    List<Work> listWorks();
    Work findWork(int id);
    Work addWork(Work work);
}
