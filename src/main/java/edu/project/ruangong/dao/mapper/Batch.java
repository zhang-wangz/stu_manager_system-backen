package edu.project.ruangong.dao.mapper;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import java.util.Date;


@Data
@Entity
public class Batch {

  @Id
  @Column(name = "batchid")
  private String batchid;

  @Column(name = "endtime")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @Future(message = "请确定一个未来的开始时间")
  private Date endtime;


}
