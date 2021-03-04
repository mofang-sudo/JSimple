package com.jsimple.community.mapper;


import com.jsimple.community.model.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountExtMapper {

    int incScore(UserAccount userAccount);
    int decScore(UserAccount userAccount);
}
