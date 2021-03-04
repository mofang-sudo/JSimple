package com.jsimple.community.api;


import com.jsimple.community.annotation.UserLoginToken;
import com.jsimple.community.dto.ThumbDTO;
import com.jsimple.community.dto.UserDTO;
import com.jsimple.community.service.LikeService;
import com.jsimple.community.vo.ThumbVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/like")
@Api(tags={"点赞/收藏接口"})
public class LikeApi {
    @Autowired
    private LikeService likeService;

    @UserLoginToken
    @ApiOperation(value = "新增收藏",notes = "只有当作者登录后才能收藏")
    @PostMapping("/insert")
    @ResponseBody
    public Object insert(HttpServletRequest request
           ,@RequestBody @Valid ThumbVO thumbVO) {

        UserDTO user = (UserDTO) request.getAttribute("loginUser");
        ThumbDTO thumbDTO = new ThumbDTO();
        BeanUtils.copyProperties(thumbVO, thumbDTO);
        thumbDTO.setGmtCreate(System.currentTimeMillis());
        thumbDTO.setGmtModified(thumbDTO.getGmtCreate());
        thumbDTO.setUser(user);
       return likeService.insert(thumbDTO);
    }

    @UserLoginToken
    @ApiOperation(value = "取消收藏",notes = "只有当作者登录后才能取消收藏")
    @DeleteMapping("/remove")
    @ResponseBody
    public Object removeLikeById(HttpServletRequest request
            ,@RequestBody @Valid ThumbVO thumbVO
    ) {
        UserDTO userDTO = (UserDTO) request.getAttribute("loginUser");
        ThumbDTO thumbDTO = new ThumbDTO();
        BeanUtils.copyProperties(thumbVO, thumbDTO);
        thumbDTO.setUser(userDTO);
        return likeService.remove(thumbDTO);
    }
}



