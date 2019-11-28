package edu.project.ruangong.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserFormLogin {
    @NotNull(message = "学号不可为空")
    private String  uid;

    @NotEmpty(message = "密码不可为空")
    private String pwd;
}
