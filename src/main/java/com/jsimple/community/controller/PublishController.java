package com.jsimple.community.controller;


import com.jsimple.community.annotation.UserLoginToken;
import com.jsimple.community.cache.TagCache;
import com.jsimple.community.dto.QuestionDTO;
import com.jsimple.community.dto.ResultDTO;
import com.jsimple.community.dto.UserDTO;
import com.jsimple.community.exception.CustomizeErrorCode;
import com.jsimple.community.exception.CustomizeException;
import com.jsimple.community.mapper.QuestionMapper;
import com.jsimple.community.model.Question;
import com.jsimple.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;


    @UserLoginToken
    @GetMapping("p/publish")
    public String publish2(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "p/add";
    }

    @UserLoginToken
    @PostMapping("p/publish")
    public String doPublish2(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("tag") String tag,
            @RequestParam("column2") Integer column2,
            @RequestParam("permission") Integer permission,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model
    ){

        String defaultDescription = "<p id=\"descriptionP\"></p>";
        description = description.replaceAll("<p id=\"descriptionP\"></p>", ""); //剔出每次编辑产生的冗余p标签
        title = title.trim();
        tag = tag.trim();
        model.addAttribute("title",title);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("column2", column2);
        model.addAttribute("id", id);
        model.addAttribute("navtype", "publishnav");
        model.addAttribute("permission", permission);
        UserDTO user = (UserDTO)request.getAttribute("loginUser");

        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "p/add";
        }
        if (description == null || defaultDescription.equals(description)) {
            model.addAttribute("error", "问题补充不能为空");
            return "p/add";
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "p/add";
        }

        Question question = new Question();
        question.setPermission(permission);
        question.setColumn2(column2);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question,user);
        return "redirect:/jsimple";
    }

    @GetMapping("p/publish/{id}")
    public String edit2(@PathVariable(name = "id") Long id,
                        Model model,
                        HttpServletRequest request){
        QuestionDTO question = questionService.getById(id,0L);
        System.out.println(request.getAttribute("loginUser"));
        UserDTO user = (UserDTO)request.getAttribute("loginUser");
        if (question.getCreator().longValue() != user.getId().longValue() ){
            throw new CustomizeException(CustomizeErrorCode.CAN_NOT_EDIT_QUESTION);
        }
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        System.out.println(question.getColumn2());
        model.addAttribute("column2", question.getColumn2());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("navtype", "publishnav");
        return "p/add";
    }

}
