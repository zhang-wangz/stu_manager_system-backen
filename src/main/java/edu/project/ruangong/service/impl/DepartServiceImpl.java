package edu.project.ruangong.service.impl;

import edu.project.ruangong.repo.DepartRepo;
import edu.project.ruangong.service.DepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartServiceImpl implements DepartService {

    @Autowired
    private DepartRepo departRepo;

    public  String getDepIdByName(String name){
        return departRepo.findDepByDepartmentname(name).getDepartmentid();
    }

}
