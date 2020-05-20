package main.service;

import main.entity.Master;
import main.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private MasterRepository masterRepository;

    @Override
    public List<Master> getAll() {
        return (List<Master>) masterRepository.findAll();
    }

    @Override
    public Master get(long id) {
        return masterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Master not found"));
    }

    @Override
    public void update(Master master, long id) {
        if (masterRepository.findById(id).isPresent()) {
            master.setId(id);
            masterRepository.save(master);
        } else {
            throw new EntityNotFoundException("Master not found");
        }
    }

    @Override
    public void save(Master master) {
        masterRepository.save(master);
    }

    @Override
    public void delete(long id) {
        masterRepository.deleteById(id);
    }
}
