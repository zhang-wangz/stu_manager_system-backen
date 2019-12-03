package edu.project.ruangong.service;


import edu.project.ruangong.repo.DepartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface DepartService {

    String getDepIdByName(String name);
}
