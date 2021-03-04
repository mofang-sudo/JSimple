package com.jsimple.community.controller;

import com.jsimple.community.cache.HotTagCache;
import com.jsimple.community.cache.LoginUserCache;
import com.jsimple.community.dto.PaginationDTO;
import com.jsimple.community.dto.QuestionDTO;
import com.jsimple.community.dto.UserDTO;
import com.jsimple.community.model.User;
import com.jsimple.community.model.UserAccount;
import com.jsimple.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private LoginUserCache loginUserCache;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request,
                        Model model) {
        return "redirect:/jsimple";
    }

    @GetMapping(value = {"/jsimple"})
    public String forum(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "15")Integer size,
                        @RequestParam(name = "column", required = false) Integer column2,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", required = false) String sort) {
        UserDTO loginuser = (UserDTO) request.getAttribute("loginUser");
        UserAccount userAccount =null;
        if(loginuser!=null){
            userAccount = new UserAccount();
            BeanUtils.copyProperties(loginuser,userAccount);
            userAccount.setUserId(loginuser.getId());
        }
        List<QuestionDTO> topQuestions = questionService.listTopwithColumn(search, tag, sort,column2);
        PaginationDTO pagination = questionService.listwithColumn(search, tag, sort, page,size,column2,userAccount);
        List<String> tags = hotTagCache.getHots();
        List<User> loginUsers = loginUserCache.getLoginUsers();
        model.addAttribute("loginUsers", loginUsers);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tags);
        model.addAttribute("sort", sort);
        model.addAttribute("column", column2);
        model.addAttribute("topQuestions", topQuestions);
        model.addAttribute("navtype", "communitynav");
        return "index";
    }
}
