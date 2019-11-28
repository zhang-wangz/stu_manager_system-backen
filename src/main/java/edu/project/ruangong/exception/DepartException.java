package edu.project.ruangong.exception;

import edu.project.ruangong.enums.DepartEnum;
import edu.project.ruangong.enums.UserEnum;

public class DepartException extends RuntimeException {
    private  Integer code;

    public DepartException(Integer code,String msg){

        super(msg);
        this.code = code;
    }

    public DepartException(DepartEnum departEnum){

        super(departEnum.getMsg());
        this.code = departEnum.getCode();
    }

    public DepartException(String msg){
        super(msg);
    }
}
