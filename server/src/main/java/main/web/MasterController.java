package main.web;

import main.entity.Master;
import main.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/master")
public class MasterController {
    @Autowired
    private MasterService masterService;

    @PostMapping("")
    public ResponseEntity<Master> saveMaster(@RequestBody @Valid Master master) {
        try {
            masterService.save(master);
            return new ResponseEntity<>(master, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Master> deleteMaster(@PathVariable("id") long id) {
        try {
            Master master = masterService.get(id);
            masterService.delete(id);
            return new ResponseEntity<>(master, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Master> updateMaster(@RequestBody @Valid Master master, @PathVariable("id") long id) {
        try {
            masterService.update(master, id);
            return new ResponseEntity<>(master, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Master>> getAllMasters() {
        List<Master> masters = masterService.getAll();
        if (masters.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(masters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Master> getMaster(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(masterService.get(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public void setMasterService(MasterService masterService) {
        this.masterService = masterService;
    }
}
