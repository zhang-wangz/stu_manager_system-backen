package edu.project.ruangong.dao.mapper;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
public class Judgestu {
  @Id
  @Column(name = "id")
  private long id;

  @NotEmpty(message = "batchid不可为空")
  @Column(name = "batchid")
  private String batchid;

  @NotEmpty(message = "uid不可为空")
  @Column(name = "uid")
  private String uid;

  @NotEmpty(message = "score不可为空")
  @Column(name = "score")
  private String score;

  @NotEmpty(message = "judgeuid不可为空")
  @Column(name = "judgeuid")
  private String judgeuid;
}
