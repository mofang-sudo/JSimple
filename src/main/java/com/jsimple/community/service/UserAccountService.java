package com.jsimple.community.service;

import com.jsimple.community.mapper.UserAccountMapper;
import com.jsimple.community.model.UserAccount;
import com.jsimple.community.model.UserAccountExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    public UserAccount selectUserAccountByUserId(Long userId) {
        UserAccountExample userAccountExample = new UserAccountExample();
        userAccountExample.createCriteria().andUserIdEqualTo(userId);
        List<UserAccount> userAccounts = userAccountMapper.selectByExample(userAccountExample);
        UserAccount userAccount = userAccounts.get(0);
        return userAccount;
    }

    public boolean isAdminByUserId(Long userId){
        if(selectUserAccountByUserId(userId).getGroupId()>=18) return true;
        else return false;

    }
}
