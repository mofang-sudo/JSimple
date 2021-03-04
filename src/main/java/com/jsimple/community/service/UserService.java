package com.jsimple.community.service;

import com.jsimple.community.dto.ResultDTO;
import com.jsimple.community.dto.UserDTO;
import com.jsimple.community.exception.CustomizeErrorCode;
import com.jsimple.community.mapper.UserAccountMapper;
import com.jsimple.community.mapper.UserInfoMapper;
import com.jsimple.community.mapper.UserMapper;
import com.jsimple.community.model.User;
import com.jsimple.community.model.UserAccount;
import com.jsimple.community.model.UserExample;
import com.jsimple.community.model.UserInfo;
import com.jsimple.community.utils.TokenUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private Environment env;

    @Value("${site.password.salt}")
    private String salt;

    public Object registerOrLoginWithMail(String mail,String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(mail);
        List<User> users = userMapper.selectByExample(userExample);
        User user = new User();
        if(password != null){
            user.setPassword(DigestUtils.sha256Hex(password + salt));
        }
        if(users.size() != 0){//登录
            User dbUser = users.get(0);
            UserDTO userDTO = getUserDTO(dbUser);
            if(dbUser.getName()==null|| StringUtils.isBlank(dbUser.getName()))//数据库为空，当前为空
                user.setName(getUserName("邮箱"));
            user.setGmtModified(System.currentTimeMillis());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(user, example);
            return ResultDTO.okOf(tokenUtils.getToken(userDTO));
        }else{//注册
            user.setEmail(mail);
            user.setName(getUserName("邮箱"));
            user.setAvatarUrl("/images/avatar/" + (int)(Math.random()*11) + ".jpg");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            UserExample example = new UserExample();
            example.createCriteria().andEmailEqualTo(mail);
            List<User> insertUsers = userMapper.selectByExample(example);
            if(insertUsers.size() != 0){
                User insertUser = insertUsers.get(0);
                initUserTable(insertUser,new UserInfo());
                UserDTO userDTO = getUserDTO(insertUser);
                return ResultDTO.okOf(tokenUtils.getToken(userDTO));
            }
        }
        return ResultDTO.errorOf("未知错误");
    }

    public String getUserName(String authorizeSize) {
        String str = RandomStringUtils.random(5,
                "abcdefghijklmnopqrstuvwxyz1234567890");
        String name = authorizeSize+"用户_"+str;
        return name;
    }

    public void initUserTable(User user, UserInfo userInfo){
        userInfo.setUserId(user.getId());
        // System.out.println(userInfo);
        userInfoMapper.insert(userInfo);
        UserAccount userAccount = new UserAccount();
        userAccount = initUserAccount(userAccount);
        userAccount.setUserId(user.getId());
        // System.out.println(userAccount);
        userAccountMapper.insert(userAccount);
        userInfo=null;
        userAccount=null;
    }

    public UserAccount initUserAccount(UserAccount userAccount){
        userAccount.setGroupId(1);
        userAccount.setVipRank(0);
        userAccount.setScore(0);
        userAccount.setScore1(0);
        userAccount.setScore2(0);
        userAccount.setScore3(0);
        return userAccount;
    }

    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        UserAccount userAccount = userAccountService.selectUserAccountByUserId(user.getId());
        userDTO.setGroupId(userAccount.getGroupId());
        userDTO.setVipRank(userAccount.getVipRank());
        return userDTO;
    }

    public UserDTO getUserDTO(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        UserAccount userAccount = userAccountService.selectUserAccountByUserId(user.getId());
        userDTO.setGroupId(userAccount.getGroupId());
        userDTO.setVipRank(userAccount.getVipRank());
        userDTO.setGroupStr(env.getProperty("user.group.r"+userAccount.getGroupId()));
        return userDTO;
    }

    public int updateUsernameById(Long userId,String username){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user.getName().equals(username)) return 1;
        else user.setName(username);
        return userMapper.updateByPrimaryKey(user);
    }

    public User selectUserByUserId(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    public Object updateUserMailById(String userId,String mail) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andEmailEqualTo(mail);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0){
            return ResultDTO.errorOf(CustomizeErrorCode.SUBMIT_MAIL_FAILED);
        }
        User updateUser = new User();
        updateUser.setEmail(mail);
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdEqualTo(Long.parseLong(userId));
        try{
            userMapper.updateByExampleSelective(updateUser, example);
            return ResultDTO.okOf("邮箱绑定/更新成功！！！");
        }catch (Exception e){
            return ResultDTO.errorOf(CustomizeErrorCode.SUBMIT_MAIL_FAILED);
        }

    }

    public ResultDTO repass(Long user_id,String nowpass,String pass){
        User user = userMapper.selectByPrimaryKey(user_id);
        if((StringUtils.isBlank(nowpass)&&StringUtils.isBlank(user.getPassword()))||DigestUtils.sha256Hex(nowpass+salt).equals(user.getPassword())){
            user.setPassword(DigestUtils.sha256Hex(pass+salt));
            int i = userMapper.updateByPrimaryKeySelective(user);
            if(i>0) return ResultDTO.okOf("修改成功");
        }
        return ResultDTO.errorOf("当前密码错误");
    }

    public User selectUserByUserId(String userId) {
        Long id = Long.parseLong(userId);
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
