package edu.project.ruangong.enums;

import lombok.Getter;

/**
 * @author athonyw
 * @version init
 * @date 2019/11/27 8:21 上午
 */
@Getter
public enum isjudgeEnum implements EnumCode {
    YITONGGUO(1,"已通过审核"),
    WEITONGGUO(0,"未通过审核"),
    WEISHENHE(null,"未进行审核")
    ;
    private Integer code;
    private String msg;

    isjudgeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
