package edu.project.ruangong.dao.mapper;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Entity
@Data
public class User {

  @Id
  private String uid;
  private String username;
  private String pwd;
  private String branch;
  private String classId;
  private String sex;
  private String phonenumber;
  private String email;
  private String rank;
  private String departments;
  private Integer activecode = 0;
  private Integer softdelete = 0;
  private Date leavetime;
  private Date appointmentTime = new Date(System.currentTimeMillis());
  private String extr;

  public String getUid() {
    return uid;
  }
  public String getDepartments() {
    return uid;
  }
}
