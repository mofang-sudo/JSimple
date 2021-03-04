package com.jsimple.community.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jsimple.community.annotation.UserLoginToken;
import com.jsimple.community.dto.PaginationDTO;
import com.jsimple.community.dto.UserDTO;
import com.jsimple.community.exception.CustomizeErrorCode;
import com.jsimple.community.exception.CustomizeException;
import com.jsimple.community.model.User;
import com.jsimple.community.model.UserAccount;
import com.jsimple.community.model.UserInfo;
import com.jsimple.community.service.QuestionService;
import com.jsimple.community.service.UserAccountService;
import com.jsimple.community.service.UserInfoService;
import com.jsimple.community.service.UserService;
import com.jsimple.community.utils.CookieUtils;
import com.jsimple.community.utils.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private CookieUtils cookieUtils;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/user/set/{action}")
    @ResponseBody
    public Map<String,Object> set(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable(name = "action") String action
            , @RequestParam(name = "json",required = false) String json
            , @RequestParam(name = "data",required = false) String data
    ){
        UserDTO user = (UserDTO) request.getAttribute("loginUser");

        if(user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        Map<String,Object> map  = new HashMap<>();

        if("info".equals(action)){
            UserInfo userInfo = JSON.parseObject(json, UserInfo.class);
            JSONObject obj=JSON.parseObject(json);
            userInfo.setLocation(obj.getString("P1")+"-"+obj.getString("C1")+"-"+obj.getString("A1"));
            String[] birthday = userInfo.getBirthday().split("-");
            if(birthday.length==3) {
                String  constellation = userInfoService.getConstellation(Integer.parseInt(birthday[1]), Integer.parseInt(birthday[2]));
                userInfo.setConstellation(constellation);
            }
            int i = userInfoService.updateByUserId(userInfo,user.getId());
            int j = userService.updateUsernameById(user.getId(),obj.getString("username"));

            if(j!=1){
                map.put("code",500);
                map.put("msg","妈呀，昵称修改失败啦！");
            }
            else if(i!=1) {
                map.put("code",500);
                map.put("msg","妈呀，资料修改失败啦！");
            }
            else{
                User dbUser = userService.selectUserByUserId(user.getId());
                UserDTO userDTO = userService.getUserDTO(dbUser);
                Cookie cookie = cookieUtils.getCookie("token",tokenUtils.getToken(userDTO),86400*3);
                response.addCookie(cookie);
                map.put("code",200);
                map.put("msg","恭喜您，资料修改成功！！！");
            }
        }
        return map;
    }

    @UserLoginToken
    @ResponseBody//@ResponseBody返回json格式的数据
    @RequestMapping(value = "/api/user/repass", method = RequestMethod.POST)
    public Object modifyPw(HttpServletRequest request,
                           @RequestParam("nowpass") String nowpass,
                           @RequestParam("pass") String pass) {
        UserDTO user = (UserDTO) request.getAttribute("loginUser");
        return userService.repass(user.getId(),nowpass,pass);
    }

    @GetMapping("/user/{userId}")
    public String getUserHome(@PathVariable(name="userId",required = false) String userId,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "12") Integer size,
                              Model model,
                              HttpServletRequest request) {
        //System.out.println("用户id为："+userId);
        if (userId == null|| StringUtils.isBlank(userId)) {
            System.out.println("用户为空!");
            return "redirect:/";
        }
        User user = userService.selectUserByUserId(userId);
        if (user != null) {
            UserAccount userAccount = userAccountService.selectUserAccountByUserId(user.getId());
            UserInfo userInfo = userInfoService.selectByUserId(user.getId());
            model.addAttribute("user", user);
            model.addAttribute("userAccount", userAccount);
            model.addAttribute("userInfo", userInfo);
            PaginationDTO paginationDTO = questionService.listByUserId(user.getId(), page, size);
            model.addAttribute("paginationDto", paginationDTO);
            return "user/home";
        }
        else {
            model.addAttribute("errorCode", CustomizeErrorCode.USER_IS_EMPTY.getCode());
            model.addAttribute("message",CustomizeErrorCode.USER_IS_EMPTY.getMessage());
            return "error";
        }
        //return "user/home";
    }
}
