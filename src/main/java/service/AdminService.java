package service;

import domain.Admin;
import domain.Area;
import domain.User;

public interface AdminService {
    public Admin login(Admin LoginAdmin);

    public boolean changeAdminEmail(Admin adminLogin, String oldEmail, String newEmail);

    public boolean changeAdminPassword(Admin adminLogin, String oldPassword, String newPassword);

    public User userManage(String studentNumber,String method);

    public int areaManage(Area area, String method);
}
