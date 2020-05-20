package main.web;

import main.dto.WorkDto;
import main.entity.Work;
import main.service.CarService;
import main.service.MasterService;
import main.service.ServiceService;
import main.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkService workService;

    @Autowired
    private MasterService masterService;

    @Autowired
    private CarService carService;

    @Autowired
    private ServiceService serviceService;

    @PostMapping("")
    public ResponseEntity<WorkDto> saveWork(@RequestBody @Valid WorkDto workDto) {
        try {
            workService.save(workFromDto(workDto));
            //workDto.setId(work.getId());
            return new ResponseEntity<>(workDto, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkDto> deleteWork(@PathVariable("id") long id) {
        try {
            Work work = workService.get(id);
            WorkDto workDto = dtoFromWork(work);
            workService.delete(id);
            return new ResponseEntity<>(workDto, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkDto> updateWork(@RequestBody @Valid WorkDto workDto, @PathVariable("id") long id) {
        try {
            Work work = workFromDto(workDto);
            workService.update(work, id);
            workDto.setId(id);
            return new ResponseEntity<>(workDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<WorkDto>> getAllWorks() {
        List<Work> works = workService.getAll();
        if (works.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<WorkDto> worksDto = works.stream().map(this::dtoFromWork).collect(Collectors.toList());
        return new ResponseEntity<>(worksDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDto> getWork(@PathVariable("id") long id) {
        try {
            WorkDto workDto = dtoFromWork(workService.get(id));
            return new ResponseEntity<>(workDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private WorkDto dtoFromWork(Work work) {
        WorkDto workDto = new WorkDto();
        workDto.setId(work.getId());
        workDto.setDate(work.getDate());
        workDto.setMasterId(work.getMaster().getId());
        workDto.setCarId(work.getCar().getId());
        workDto.setServiceId(work.getService().getId());
        return workDto;
    }

    private Work workFromDto(WorkDto workDto) {
        Work work = new Work();
        work.setDate(workDto.getDate());
        work.setMaster(masterService.get(workDto.getMasterId()));
        work.setCar(carService.get(workDto.getCarId()));
        work.setService(serviceService.get(workDto.getServiceId()));
        return work;
    }

    @Autowired
    public void setWorkService(WorkService workService) {
        this.workService = workService;
    }
}
