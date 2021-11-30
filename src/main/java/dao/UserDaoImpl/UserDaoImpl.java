package dao.UserDaoImpl;

import dao.UserDao;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JdbcUtils;
import util.UuidUtil;

import java.util.Map;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public User findByStudentNumberAndPassword(String studentNumber, String passWord) {
        try {
            //只能封装一条记录
            String sql = "select * from user where studentnumber=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), studentNumber, passWord);
            System.out.println(user + " from UserDaoImpl");
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User getPasswordByStudentNumber(String studentNumber) {
        String sql = "select * from user where studentnumber=?";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), studentNumber);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int save(User user) {
        String sql = "insert into user(studentnumber,username,password,email,acode) values(?,?,?,?,?)";
        int update = template.update(sql,
                user.getStudentnumber(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                UuidUtil.getUuid());
        if (update != 0) {
            //表明执行成功
            return 1;
        } else {
            //表明执行失败
            return 0;
        }
    }

    @Override
    public int findByStudentNumber(String studentNumber) {
        String sql = "select COUNT('studentnumber') from user where studentnumber=?";
        long queryStudentNumber = 0;

        try {
            Map<String, Object> map = template.queryForMap(sql, studentNumber);
            queryStudentNumber = (long) map.get("COUNT('studentnumber')");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (queryStudentNumber != 0) {
            //代表此用户已存在
            return 1;
        } else {
            //代表此用户还未注册过
            return 0;
        }
    }

    @Override
    public String findUseracode(String StudentNumber) {
        String sql = "select acode from user where studentnumber=?";
        try {
            Map<String, Object> map = template.queryForMap(sql, StudentNumber);
            String acode = (String) map.get("acode");
            return acode;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String findUserEmail(String StudentNumber) {
        String sql = "select email from user where studentnumber=?";
        try {
            Map<String, Object> map = template.queryForMap(sql, StudentNumber);
            String email = (String) map.get("email");
            return email;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User findUserByAcode(String acode) {
        User user = null;

        String sql = "select * from user where acode = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), acode);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateIsLocked(User unlockUser) {
        String sql = "update user set islocked= 'no' where acode=?";
        try {
            template.update(sql, unlockUser.getAcode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateIsLockedYes(String StudentNumber) {
        String sql = "update user set islocked= 'yes' where studentnumber=?";
        try {
            template.update(sql, StudentNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int checkEmailOnly(String email) {
        String sql = "select COUNT('email') from user where email=?";
        long queryEmail = 0;

        try {
            Map<String, Object> map = template.queryForMap(sql, email);
            queryEmail = (long) map.get("COUNT('email')");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (queryEmail != 0) {
            //代表此邮箱已存在
            return 1;
        } else {
            //代表此邮箱还未被使用过
            return 0;
        }
    }

    @Override
    public boolean resetUserAcode(String StudentNumber) {
        String sql = "update user set acode=? where studentnumber=?";
        try {
            template.update(sql, UuidUtil.getUuid(), StudentNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateUserEmail(String StudentNumber, String newEmail) {
        String sql = "update user set email=? where studentnumber=?";
        try {
            template.update(sql, newEmail, StudentNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean updateUserPassword(String StudentNumber, String newPassword) {
        String sql = "update user set password=? where studentnumber=?";
        try {
            template.update(sql,newPassword,StudentNumber);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
