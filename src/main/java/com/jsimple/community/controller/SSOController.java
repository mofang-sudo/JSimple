package com.jsimple.community.controller;

import com.jsimple.community.dto.UserDTO;
import com.jsimple.community.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SSOController {

    @Autowired
    private CookieUtils cookieUtils;

    @Value("${vaptcha.vid}")
    private String vaptcha_vid;

    @RequestMapping("/sso/{action}")
    public String aOuth(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable(name = "action") String action,
                        Model model) {
        UserDTO user = (UserDTO)request.getAttribute("loginUser");
        if(user != null) {
            return "redirect:/jsimple";
        }
        model.addAttribute("vaptcha_vid", vaptcha_vid);
        if("login".equals(action)){
            model.addAttribute("initOssType", 3);
            model.addAttribute("section", "login");
            model.addAttribute("sectionName", "登录");
            // return "/user/login";
        }
        else if("register".equals(action)){
            model.addAttribute("initOssType", 2);
            model.addAttribute("section", "register");
            model.addAttribute("sectionName", "注册");
            //  return "/user/reg";
        }
        else if("reset".equals(action)){
            model.addAttribute("initOssType", 2);
            model.addAttribute("section", "register");
            model.addAttribute("sectionName", "重置密码");
            //  return "/user/reg";
        }
        else {
            return "redirect:/jsimple";
        }
        return "user/sso";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {

      /*  Cookie cookie = new Cookie("token", null);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(0);*/
        Cookie cookie = cookieUtils.getCookie("token",null,0);
        response.addCookie(cookie);
        return "redirect:/jsimple";
    }
}
