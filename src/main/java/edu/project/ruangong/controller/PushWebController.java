package edu.project.ruangong.controller;

/**
 * @author athonyw
 * @version init
 * @date 2019/11/19 3:43 下午
 */

import edu.project.ruangong.server.WebSocketServer;
import edu.project.ruangong.utils.ResultUtil;
import edu.project.ruangong.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/socket")
public class PushWebController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/pushWeb")
    public ResultVO pushVideoListToWeb(String message, String username) {
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            WebSocketServer.sendInfo("有新用户"+username+"连接,message:" + message);
            res.put("username",username);
            res.put("operationResult", true);
        } catch (Exception e) {
            res.put("operationResult", false);
            res.put("username",null);
            return ResultUtil.error(res);
        }
        return ResultUtil.success(res);
    }
}
