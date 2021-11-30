package service;

import domain.AreaShow;
import domain.User;

import java.util.ArrayList;

public interface UserService {
    //用户登录
    public User login(User loginUser);

    //用户注册
    public int regist(User registUser);

    //检查此邮箱是否被注册
    public int checkEmailRegistry(String email);

    //检查此邮箱有无绑定的用户
    public int checkStudentNumberRegistry(String email);

    //向用户发送激活邮件
    public void sendActiveMailToUser(String StudentNumber);

    //用户激活用户账号，从邮箱注册邮件网页进入
    public boolean activeUser(String acode);

    //用户修改邮箱，重设邮箱，先锁定用户，并发送激活邮件到新邮箱
    public boolean userChangeEmail(User userLogin,String oldEmail,String newEmail);

    //用户修改密码
    public boolean userChangePassword(User userLogin,String oldPassword,String newPassword);

    //用户获取所有可用活动区域
    public ArrayList<AreaShow> getAllUseableAreas();
}
