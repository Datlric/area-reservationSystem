package dao;

import domain.Area;
import domain.User;

import java.util.List;

public interface UserDao {
    //登录功能，根据用户学号及密码查找唯一用户，成功返回登录用户所有信息
    public User findByStudentNumberAndPassword(String studentNumber, String passWord);

    //根据学号获取对应的学号帐户密码
    public User getPasswordByStudentNumber(String studentNumber);

    //注册用户，保存用户信息
    public int save(User user);

    //检查学号是否被注册
    public int findByStudentNumber(String studentNumber);

    //根据学号找对应帐户的激活码
    public String findUseracode(String StudentNumber);


    public String findUserEmail(String StudentNumber);

    public User findUserByAcode(String acode);

    //设定用户锁定状态为no
    public void updateIsLocked(User unlockUser);

    //设定用户锁定状态为yes
    public boolean updateIsLockedYes(String StudentNumber);

    public boolean updateUserEmail(String StudentNumber, String newEmail);

    //更新用户密码
    public boolean updateUserPassword(String StudentNumber, String newPassword);

    //重设用户激活码acode
    public boolean resetUserAcode(String StudentNumber);

}
