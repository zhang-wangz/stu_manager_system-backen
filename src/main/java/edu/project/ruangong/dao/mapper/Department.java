package edu.project.ruangong.dao.mapper;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;


@Entity
@Data
public class Department {

  @Id
  private String departmentid;
  private String departmentname;
  private String ministerid;
  private String chairmanid;
  private int peoplenumber;
  private String introduction;

  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private java.sql.Date createtime = new Date(System.currentTimeMillis());

  private java.sql.Date deletetime;

  public String getMinisterid(){
    return this.ministerid;
  }

  public String getDepartmentname(){
    return this.ministerid;
  }
}