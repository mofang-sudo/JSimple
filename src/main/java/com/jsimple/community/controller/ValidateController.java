package com.jsimple.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.jsimple.community.dto.ValidateDTO;
import com.jsimple.community.provider.VaptchaProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ValidateController {


    @ResponseBody//@ResponseBody返回json格式的数据
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public Object post(@RequestParam(name = "token", required = false) String token,
                       @RequestParam(name = "scene", required = false) int scene,
                       @RequestParam(name = "ip", required = false) String ip) {
        String json = VaptchaProvider.getValidateResult(token,scene,ip);

        JSONObject obj = JSONObject.parseObject(json);
        Integer success = obj.getInteger("success");
        Integer score = obj.getInteger("score");
        String msg = obj.getString("msg");
        ValidateDTO validateDTO = new ValidateDTO();
        validateDTO.setMsg(msg);
        validateDTO.setSocre(score);
        validateDTO.setSuccess(success);
       System.out.println("result:"+success+score+msg);
        //return ResultDTO.okOf("验证成功！");
        return validateDTO;
    }

}
