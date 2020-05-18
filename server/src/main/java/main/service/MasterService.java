package main.service;

import main.entity.Master;

import java.util.List;

public interface MasterService {
    List<Master> listMasters();
    Master findMaster(int id);
    Master addMaster(Master master);
}
