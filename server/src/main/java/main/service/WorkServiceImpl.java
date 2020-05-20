package main.service;

import main.entity.Work;
import main.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    WorkRepository workRepository;

    @Override
    public List<Work> getAll() {
        return (List<Work>) workRepository.findAll();
    }

    @Override
    public Work get(long id) {
        return workRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Work not found"));
    }

    @Override
    public void update(Work work, long id) {
        if (workRepository.findById(id).isPresent()) {
            work.setId(id);
            workRepository.save(work);
        } else {
            throw new EntityNotFoundException("Work not found");
        }
    }

    @Override
    public void save(Work work) {
        workRepository.save(work);
    }

    @Override
    public void delete(long id) {
        workRepository.deleteById(id);
    }
}
