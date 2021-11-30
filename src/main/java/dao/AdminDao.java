package dao;

import domain.Admin;
import domain.User;

public interface AdminDao {
    //根据AdminId找Admin
    public Admin findByAdminId(String AdminId);

    //根据管理员id更改管理员邮箱
    public boolean updateAdminEmailById(String adminId,String newEmail);

    //根据管理员id更改管理员密码
    public boolean updateAdminPasswordById(String adminId,String newPassword);

    public User getUserByStudentNumber(String studentNumber);

    public User resetUserPassword(String studentNumber);
}
