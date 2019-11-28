package edu.project.ruangong.enums;


import edu.project.ruangong.enums.EnumCode;
import lombok.Getter;

@Getter
public enum  UserEnum implements EnumCode {
    CREATE_FAIL_BLANK(-1,"创建用户失败"),
    LOGIN_FAIL(-4,"因信息填写错误登陆失败"),
    USER_PWD_BLANK(-2,"用户密码不正确"),
    USER_UID_BLANK(-3,"学号输入错误"),
    EDIT_INFO_WRONG(-5,"编辑信息时信息不正确")
    ;

    private Integer code;
    private String msg;

    UserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
