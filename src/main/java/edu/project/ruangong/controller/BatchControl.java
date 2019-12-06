package edu.project.ruangong.controller;

import edu.project.ruangong.dao.mapper.Batch;
import edu.project.ruangong.repo.BatchRepo;
import edu.project.ruangong.utils.KeyUtil;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+:08:00"));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

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
