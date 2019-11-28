package edu.project.ruangong.enums;


import lombok.Getter;

@Getter
public enum  DepartEnum implements EnumCode{
    INFO_DEPART_FAIL(-1,"部门信息填写错误"),
    DEPART_PEOPLEUID_EMPTY(-4,"部长学号或者分管主席学号错误"),
    BACK_WRONG(-5,"获取信息返回错误")
    ;
    private Integer code;
    private String msg;

    DepartEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
