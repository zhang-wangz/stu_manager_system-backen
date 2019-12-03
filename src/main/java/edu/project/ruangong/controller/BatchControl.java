package edu.project.ruangong.controller;

import com.sun.org.apache.xml.internal.security.keys.KeyUtils;
import edu.project.ruangong.dao.mapper.Batch;
import edu.project.ruangong.repo.BatchRepo;
import edu.project.ruangong.utils.KeyUtil;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author athonyw
 * @version init
 * @date 2019/12/3 3:52 下午
 */
@RestController
@RequestMapping("/batch")
public class BatchControl {
    @Autowired
    private BatchRepo batchRepo;

    @PostMapping("addBatch")
    public ResultVO addBatch(@Valid Batch batch,
                             BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()) return ResultUtil.error(-1,bindingResult.getFieldError().toString());
        Batch batch1  = new Batch();
        BeanUtils.copyProperties(batch,batch1);
        batch1.setBatchid(KeyUtil.getUniqueKey());
        batchRepo.save(batch1);
        return ResultUtil.success(batch1);
    }


    @GetMapping("getAllBatch")
    public ResultVO getAllBatch(){
        return ResultUtil.success(batchRepo.findAll());
    }
}
