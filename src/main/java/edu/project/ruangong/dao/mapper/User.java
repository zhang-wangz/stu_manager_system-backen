package edu.project.ruangong.dao.mapper;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Data
@Table(name = "user", schema = "stu_union", catalog = "")
public class User {

  @Id
  @Column(name = "uid")
  private String uid;

  @Column(name = "username")
  private String username;

  @Column(name = "pwd")
  private String pwd;

  @Column(name = "branch")
  private String branch;

  @Column(name = "classId")
  private String classId;

  @Column(name = "sex")
  private String sex;

  @Column(name = "phonenumber")
  private String phonenumber;

  @Column(name = "email")
  private String email;

  @Column(name = "rank")
  private String rank;

  @Column(name = "departments")
  private String departments;

  @Column(name = "activecode")
  private Integer activecode = 0;

  @Column(name = "softdelete")
  private Integer softdelete = 0;

  @Column(name = "leavetime")
  private Date leavetime;

  @Column(name = "appointmentTime")
  private Date appointmentTime = new Date(System.currentTimeMillis());

  @Column(name = "extr")
  private String extr;

  public String getUid() {
    return uid;
  }
  public String getDepartments() {
    return departments;
  }
}
