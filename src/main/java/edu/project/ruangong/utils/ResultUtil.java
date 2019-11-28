package edu.project.ruangong.utils;


import edu.project.ruangong.vo.ResultVO;

public class ResultUtil {
    public static ResultVO<Object> success(Object object){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setMsg("success");
        resultVO.setCode(0);
        resultVO.setDataObj(object);
        return  resultVO;
    }

    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg("success");
        resultVO.setCode(0);
        return  resultVO;
    }

    public static ResultVO success(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }

    public static ResultVO error(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setMsg("fail");
        resultVO.setCode(-1);
        resultVO.setDataObj(object);
        return  resultVO;
    }



    public static  ResultVO error(Integer code,String msg){
        ResultVO  resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
