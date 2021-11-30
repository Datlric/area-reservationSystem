package service.AdminServiceImpl;

import dao.AdminDao;
import dao.AdminDaoImpl.AdminDaoImpl;
import dao.AreaDao;
import dao.AreaDaoImpl.AreaDaoImpl;
import domain.Admin;
import domain.Area;
import domain.User;
import service.AdminService;

public class AdminServiceImpl implements AdminService {
    AdminDao adminDao = new AdminDaoImpl();
    AreaDao areaDao = new AreaDaoImpl();

    @Override
    public Admin login(Admin LoginAdmin) {
        Admin admin = adminDao.findByAdminId(LoginAdmin.getAdminid());
        if (admin == null) {
            return null;
        }

        //密码验证判断
        if (LoginAdmin.getPassword().equals(admin.getPassword())) {
            System.out.println(admin + " from AdminServiceImpl");
            return admin;
        } else {
            return null;
        }

    }

    @Override
    public boolean changeAdminEmail(Admin adminLogin, String oldEmail, String newEmail) {
        //判断管理员是否能与老邮箱匹配,用的是session里面存储的已登录管理员的信息，也就是执行当前操作的用户的信息
        if (adminLogin.getEmail().equals(oldEmail)) {
            boolean updateEmailFlag = adminDao.updateAdminEmailById(adminLogin.getAdminid(), newEmail);
            return updateEmailFlag;
        } else {
            return false;
        }
    }

    @Override
    public boolean changeAdminPassword(Admin adminLogin, String oldPassword, String newPassword) {
        //判断管理员密码是否能与老密码匹配,用的是session里面存储的已登录管理员的信息，也就是执行当前操作的用户的信息
        if (adminLogin.getPassword().equals(oldPassword)) {
            boolean updatePasswordFlag = adminDao.updateAdminPasswordById(adminLogin.getAdminid(), newPassword);
            return updatePasswordFlag;
        } else {
            return false;
        }
    }

    @Override
    public User userManage(String studentNumber, String method) {
        if ("getUserInfo".equals(method)) {
            User user = adminDao.getUserByStudentNumber(studentNumber);
            return user;
        }

        if ("resetUserPassword".equals(method)) {
            User user = adminDao.resetUserPassword(studentNumber);
            return user;
        }

        return null;
    }

    @Override
    public int areaManage(Area area, String method) {
        if (area != null) {
            //先检查数据库中是否有这个区域的信息
            Area areaByAreaId = areaDao.getAreaByAreaId(area.getAreaId());
            if (areaByAreaId != null) {
                area = areaByAreaId;
                if ("delArea".equals(method)) {
                    return areaDao.delAreaByAreaId(area.getAreaId());
                } else {
                    return -1;
                }
            } else {
                return -1;
            }

        } else {
            return -1;
        }
    }
}
