package com.jsimple.community.controller;

import com.jsimple.community.annotation.UserLoginToken;
import com.jsimple.community.dto.UserDTO;
import com.jsimple.community.utils.CookieUtils;
import com.jsimple.community.utils.TokenUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {
    @Autowired
    private CookieUtils cookieUtils;
    @Autowired
    private TokenUtils tokenUtils;

    public String getFileName(String contentType) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        String[] ss = contentType.split("/");
        String str = RandomStringUtils.random(2,
                "abcdefghijklmnopqrstuvwxyz1234567890");
        String name = timeStr + "_" +str+"."+ss[1];
        return name;
    }
}
