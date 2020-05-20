package main.service;

import main.entity.Master;
import main.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
        Optional<Master> optionalMaster = masterRepository.findById(id);
        if (optionalMaster.isPresent()) {
            return optionalMaster.get();
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
