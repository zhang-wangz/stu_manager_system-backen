package edu.project.ruangong.controller;

import edu.project.ruangong.dao.mapper.Judgestu;
import edu.project.ruangong.repo.JudgeStuRepo;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author athonyw
 * @version init
 * @date 2019/12/3 3:51 下午
 */
@RestController
@RequestMapping("/judgestu")
public class JudgeStuControl {
    @Autowired
    private JudgeStuRepo judgeStu;

    @PostMapping("addBatch")
    public ResultVO addBatch(@Valid Judgestu judgestu,
                             BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()) return ResultUtil.error(-1,bindingResult.getFieldError().toString());
        Judgestu judgestu1  = new Judgestu();
        BeanUtils.copyProperties(judgestu,judgestu1);
        judgeStu.save(judgestu1);
        return ResultUtil.success();
    }


    @GetMapping("getBatchByuid")
    public ResultVO getBatchScoreByuid(@RequestParam(name = "uid")String uid){
//            Judgestu judgestu = judgeStu.findJudgeStuByUid(uid);
//            if(judgestu==null) return ResultUtil.error(-1,"wrong");
//            return ResultUtil.success(judgestu.getScore());
        return ResultUtil.success(judgeStu.findJudgestusByUid(uid));
    }


}
