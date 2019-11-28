package edu.project.ruangong.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultVO<T>{

    private String msg;

    private Integer Code;

    private T DataObj;

    public ResultVO(String msg, Integer code, T dataObj) {
        this.msg = msg;
        Code = code;
        DataObj = dataObj;
    }
    public ResultVO() {

    }
}
