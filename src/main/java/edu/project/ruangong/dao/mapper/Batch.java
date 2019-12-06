package edu.project.ruangong.dao.mapper;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
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
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @Future(message = "请确定一个未来的开始时间")
  private Date endtime;


}
