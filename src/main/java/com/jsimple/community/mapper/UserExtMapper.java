package com.jsimple.community.mapper;


import com.jsimple.community.dto.UserQueryDTO;
import com.jsimple.community.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExtMapper {
    List<User> selectLatestLoginUser(UserQueryDTO userQueryDTO);
    Integer count(UserQueryDTO userQueryDTO);
}
