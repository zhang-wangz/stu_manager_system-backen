package edu.project.ruangong.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;


@Entity
@Data
public class UserDto {
    @Id
    private String uid;
    private String username;
    private String branch;
    private String classId;
    private String sex;
    private String phonenumber;
    private String email;
    private String rank;
    private String departments;
    private java.sql.Timestamp leavetime;
    private java.sql.Timestamp appointmentTime = new Timestamp(System.currentTimeMillis());
    private Integer activecode = 0;
//    private String jwt;
    private String extr;
}
