package edu.project.ruangong.controller;

import edu.project.ruangong.repo.JudgeRepo;
import edu.project.ruangong.repo.NoticeRepo;
import edu.project.ruangong.repo.UserBaseRepository;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author athonyw
 * @version init
 * @date 2019/12/2 5:41 下午
 */
@RequestMapping("statistic")
@RestController
public class TJController {
    @Autowired
    private UserBaseRepository userBaseRepository;

    @Autowired
    private JudgeRepo judgeRepo;

    @Autowired
    private NoticeRepo noticeRepo;

    @GetMapping("getSum")
    public ResultVO getSum(){
        java.util.Map map = new HashMap();
        int num = userBaseRepository.coutusercount();
        int num1 = judgeRepo.coutjudgecount();
        int num2 = noticeRepo.coutnoticecount();
        map.put("usercount",num);
        map.put("judgecount",num1);
        map.put("noticecount",num2);
        return ResultUtil.success(map);
    }

    @GetMapping("newuserweek")
    public ResultVO newuserweek(){
        Map map = new HashMap();
        for(int i=1;i<=7;i++){
            int num = userBaseRepository.coutusercountbyinterval(i);
            map.put("星期"+String.valueOf(i),num);
        }
        return ResultUtil.success(map);
    }

    @GetMapping("newjudgeweek")
    public ResultVO newjudgeweek(){
        Map map = new HashMap();
        for(int i=1;i<=7;i++){
            int num = judgeRepo.coutjudgecountbyinterval(i);
            map.put("星期"+String.valueOf(i),num);
        }
        return ResultUtil.success(map);
    }

    @GetMapping("newnoticeweek")
    public ResultVO newnoticeweek(){
        Map map = new HashMap();
        for(int i=1;i<=7;i++){
            int num = noticeRepo.coutnoticecountbyinterval(i);
            map.put("星期"+String.valueOf(i),num);
        }
        return ResultUtil.success(map);
    }

}
