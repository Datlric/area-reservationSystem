package dao.AdminDaoImpl;

import dao.AdminDao;
import domain.Admin;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JdbcUtils;

public class AdminDaoImpl implements AdminDao {

    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public Admin findByAdminId(String AdminId) {
        try {
            String sql = "select * from admin where adminid=?";
            Admin admin = template.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class), AdminId);
            System.out.println(admin + " from AdminDaoImpl");
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateAdminEmailById(String adminId, String newEmail) {
        String sql = "update admin set email=? where adminid=?";

        try {
            int update = template.update(sql, newEmail, adminId);
            if (update != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAdminPasswordById(String adminId, String newPassword) {

        String sql = "update admin set password=? where adminid=?";

        try {
            int update = template.update(sql, newPassword, adminId);
            if (update != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByStudentNumber(String studentNumber) {
        String sql = "select * from user where studentnumber=?";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), studentNumber);
            if (user != null) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User resetUserPassword(String studentNumber) {
        String sql = "update user set password='1234' where studentnumber=?";
        try {
            int update = template.update(sql, studentNumber);
            if (update != 0) {
                User user = new User();
                user.setAcode("1");
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
