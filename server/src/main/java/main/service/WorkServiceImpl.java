package main.service;

import main.entity.Work;
import main.exception.WorkNotFoundException;
import main.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    WorkRepository workRepository;

    @Override
    public List<Work> listWorks() {
        return (List<Work>) workRepository.findAll();
    }

    @Override
    public Work findWork(int id) {
        Optional<Work> optionalWork = workRepository.findById(id);
        if (optionalWork.isPresent()) {
            return optionalWork.get();
        } else {
            throw new WorkNotFoundException("Work not found");
        }
    }

    @Override
    public Work addWork(Work work) {
        return workRepository.save(work);
    }
}
