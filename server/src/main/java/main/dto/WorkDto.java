package main.dto;

import lombok.Data;
import main.entity.Work;
import main.service.CarService;
import main.service.MasterService;
import main.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Data
public class WorkDto {
    private long id;
    private Date date;
    private long masterId;
    private long carId;
    private long serviceId;
}
