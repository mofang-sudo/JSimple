package com.jsimple.community.controller;

import com.jsimple.community.cache.TemporaryCache;
import com.jsimple.community.dto.ResultDTO;
import com.jsimple.community.exception.CustomizeErrorCode;
import com.jsimple.community.utils.JavaMailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    @Autowired
    private TemporaryCache temporaryCache;

    @Value("${site.main.title}")
    private String siteTitle;

    @ResponseBody
    @PostMapping(value = "/mail/getMailCode")
    public Object getMailCode(@RequestParam(name = "username",required = false) String username,
                              @RequestParam(name =  "mail") String mail,
                              @RequestParam(name = "ip") String ip,
                              @RequestParam(name = "token") String token){
        try {
            if (username == null) username = siteTitle;
            JavaMailUtils.setUserName(username);
            JavaMailUtils.setReceiveMailAccount(mail);
            JavaMailUtils.send();
            System.out.println("mail:"+mail);
            System.out.println("JavaMailUtils.code:"+JavaMailUtils.code);
            temporaryCache.putMailCode(mail, JavaMailUtils.code);
            return ResultDTO.okOf("邮箱验证码已发送成功！");
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResultDTO.errorOf(CustomizeErrorCode.SEND_MAIL_FAILED);
        }
    }
}
