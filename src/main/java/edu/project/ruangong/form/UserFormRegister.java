package edu.project.ruangong.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 *
 * 这里之前写的时候和数据库映射写成了大驼峰命名方式
 * 需要修改
 */
@Data
public class UserFormRegister {

    @Pattern(regexp = "^[31][0-9].{6}",message = "学号以31开头")
    @Size(max=8,min = 8,message = "请输入正确的学号")
    private String  uid;

    @NotEmpty(message = "学生姓名不可为空")
    @Pattern(regexp = "[\\u4e00-\\u9fa5]+",message = "学生名字请输入中文")
    private String username;

    @NotEmpty(message = "密码填写不可为空")
    @Size(max = 16,min = 6,message = "密码长度为6-16")
    @Pattern(regexp = "^(?=.{6,16})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "密码必须含有大写字母、小写字母、数字")
    private String pwd;

    @NotEmpty(message = "所属分院不可为空")
    private String branch;

    @NotEmpty(message = "所属班级不可为空")
    private String classId;

    @NotEmpty(message = "性别不可为空")
    @Pattern(regexp = "^男$|^女$",message = "难道你是外星人嘛，不男又不女")
    private String sex;

    @NotEmpty(message = "手机号不可为空")
    @Pattern(regexp = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$",message = "请输入正确的手机号")
    private String phonenumber;

    @Email(message = "邮件格式不正确")
    private String email;

    @NotEmpty(message = "所属等级不可为空")
    private String rank;

    @NotEmpty(message = "所属部门不可为空")
    private String departments;
}
