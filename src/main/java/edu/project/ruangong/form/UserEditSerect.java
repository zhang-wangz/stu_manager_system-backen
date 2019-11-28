package edu.project.ruangong.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserEditSerect {

    @NotNull(message = "学号不可为空")
    private String  uid;

    @NotEmpty(message = "旧密码不可为空")
    private String oldpwd;

    @NotEmpty(message = "新密码不可为空")
    @Size(max = 16,min = 6,message = "密码长度为6-16")
    @Pattern(regexp = "^(?=.{6,16})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "密码必须含有大写字母、小写字母、数字")
    private String newpwd;
}
