package main.repository;

import main.entity.Master;
import org.springframework.data.repository.CrudRepository;

public interface MasterRepository extends CrudRepository<Master, Long> {
}
