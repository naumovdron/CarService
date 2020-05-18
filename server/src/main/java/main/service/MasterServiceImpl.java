package main.service;

import main.entity.Master;
import main.exception.MasterNotFoundException;
import main.repository.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private MasterRepository masterRepository;

    @Override
    public List<Master> listMasters() {
        return (List<Master>) masterRepository.findAll();
    }

    @Override
    public Master findMaster(int id) {
        Optional<Master> optionalMaster = masterRepository.findById(id);
        if (optionalMaster.isPresent()) {
            return optionalMaster.get();
        } else {
            throw new MasterNotFoundException("Master not found");
        }
    }

    @Override
    public Master addMaster(Master master) {
        return masterRepository.save(master);
    }
}
