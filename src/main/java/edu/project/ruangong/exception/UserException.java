package edu.project.ruangong.exception;

import edu.project.ruangong.enums.UserEnum;

public class UserException extends RuntimeException {
    private  Integer code;

    public UserException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

    public UserException(UserEnum userEnum){

        super(userEnum.getMsg());
        this.code = userEnum.getCode();
    }

    public UserException(String msg){
        super(msg);
    }
}
