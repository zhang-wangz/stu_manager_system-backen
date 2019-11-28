package edu.project.ruangong.form;

import edu.project.ruangong.dao.mapper.User;
import edu.project.ruangong.repo.UserBaseRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.Objects;

@Data
public class Departform {
    @Autowired
    private UserBaseRepository userBaseRepository;

    private String departmentid;

    @NotEmpty(message = "部长名字不可为空")
    @Pattern(regexp = "[\\u4e00-\\u9fa5]+",message = "名字请输入中文")
    private String departmentname;

    @NotEmpty(message = "部长学号不可为空")
    @Pattern(regexp = "^[31][0-9].{6}",message = "部长学号以31开头")
    private String ministerid;

    @NotEmpty(message = "分管主席学号不可为空")
    @Pattern(regexp = "^[31][0-9].{6}",message = "分管主席学号以31开头")
    private String chairmanid;

    @NotNull(message = "部门人数不可为空")
    private int peoplenumber;

    @NotEmpty(message = "部门介绍不可为空")
    private String introduction;

    private String ministername;

    private String chairmanname;


}
