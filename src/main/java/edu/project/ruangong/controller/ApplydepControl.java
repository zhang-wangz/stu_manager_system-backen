package edu.project.ruangong.controller;

import edu.project.ruangong.dao.mapper.Applydep;
import edu.project.ruangong.repo.ApplydepRepo;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author athonyw
 * @version init
 * @date 2019/12/6 2:51 下午
 */


@RestController
@RequestMapping("applydep")
public class ApplydepControl {

    @Autowired
    private ApplydepRepo applydepRepo;


    @PostMapping(path = "/addApplydep")
    public ResultVO addApplydep(@RequestBody Map<String,String> map) {
        String uid = map.get("uid");
        String depId = map.get("depId");
        String reason = map.get("reason");
        Applydep applydep = new Applydep();
        applydep.setReason(reason);
        applydep.setDepid(depId);
        applydep.setUid(uid);
        return ResultUtil.success();
    }

    @GetMapping("getapplydepBydepid")
    public ResultVO getapplydepBydepid(@RequestParam(name = "depId") String depId) {
        return ResultUtil.success(applydepRepo.findApplydepsByDepid(depId));
    }

    @GetMapping("getapplydepByuid")
    public ResultVO getapplydepByuid(@RequestParam(name = "uid") String uid) {
        return ResultUtil.success(applydepRepo.findApplydepByUid(uid));
    }

    @GetMapping("changeapplyflag")
    public ResultVO getapplydepByuid(@RequestParam(name = "key",required = true) Integer key,
                                     @RequestParam(name = "flag") Integer flag) {
        Applydep applydep = applydepRepo.findById(key).orElse(null);
        applydep.setFlag(flag);
        applydepRepo.save(applydep);
        return ResultUtil.success(applydep);
    }

}
