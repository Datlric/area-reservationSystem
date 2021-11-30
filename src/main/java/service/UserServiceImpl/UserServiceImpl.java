package service.UserServiceImpl;

import dao.AreaDao;
import dao.AreaDaoImpl.AreaDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl.UserDaoImpl;
import domain.Area;
import domain.AreaShow;
import domain.User;
import service.UserService;
import util.sendMailUtils;

import java.util.ArrayList;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    AreaDao areaDao = new AreaDaoImpl();

    @Override
    public User login(User loginUser) {
        User user = userDao.findByStudentNumberAndPassword(loginUser.getStudentnumber(), loginUser.getPassword());
        System.out.println(user + " from UserServiceImpl");
        return user;
    }

    @Override
    public int regist(User registUser) {
        //1代表成功，0代表失败
        int stauts = userDao.save(registUser);
        return stauts;
    }

    @Override
    public int checkEmailRegistry(String email) {
        //这个方法只存在userDaoImpl里面，接口里面没写
        UserDaoImpl userDaoImpl = (UserDaoImpl) userDao;
        int flag = 0;
        flag = userDaoImpl.checkEmailOnly(email);
        System.out.println(flag + " fromUserServiceImpl");
        return flag;
    }

    @Override
    public int checkStudentNumberRegistry(String StudentNumber) {
        int flag = 0;
        flag = userDao.findByStudentNumber(StudentNumber);
        System.out.println(flag + " fromUserServiceImpl");
        return flag;
    }

    @Override
    public void sendActiveMailToUser(String StudentNumber) {
        String userEmail = userDao.findUserEmail(StudentNumber);
        String useracode = userDao.findUseracode(StudentNumber);

        //todo 这里还需要写发送邮件点击激活的功能，明天接着写，记得在手机里查一下你QQ邮箱的授权码
        sendMailUtils sendMailUtils = new sendMailUtils();
        sendMailUtils.setSubject("学号为 " + StudentNumber + " 的激活邮件");
        sendMailUtils.setContent("<a href='http://localhost/race2/activeUserServlet?acode=" + useracode + "'>来点我，激活你的帐户</a>");
        sendMailUtils.send("2237126549@qq.com", userEmail, "mykccfksxehjecic");//mykccfksxehjecic是2237126549@qq.com开启IMAP代理服务的授权码，可能一月一换，得更新

    }

    @Override
    public boolean activeUser(String acode) {
        User user = userDao.findUserByAcode(acode);
        //根据激活码查询用户
        if (user != null) {
            //调用Dao曾方法修改激活状态isLocked的方法
            userDao.updateIsLocked(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean userChangeEmail(User userLogin, String oldEmail, String newEmail) {
        //判断用户学号是否能与老邮箱匹配,用的是session里面存储的已登录用户的信息，也就是执行当前操作的用户的信息
        if (userLogin.getEmail().equals(oldEmail)) {
            String StudentNumber = userLogin.getStudentnumber();
            //更新用户邮箱
            boolean updateUserEmailFlag = userDao.updateUserEmail(StudentNumber, newEmail);
            //锁定用户,设定用户状态为yes
            boolean setYesFlag = userDao.updateIsLockedYes(StudentNumber);
            //重设acode激活码
            boolean resetAcodeFlag = userDao.resetUserAcode(StudentNumber);
            //todo 11.3 这里以及下面的UserDao中的updateUserEmail方法都还未实现
            //三个条件都为true才能返回true
            return updateUserEmailFlag && setYesFlag && resetAcodeFlag;
        } else {
            return false;
        }
    }

    @Override
    public boolean userChangePassword(User userLogin, String oldPassword, String newPassword) {
        //判断当前登录用户的session中的登陆密码是否能和它输入的老密码一致
        if (userLogin.getPassword().equals(oldPassword)) {
            String StudentNumber = userLogin.getStudentnumber();
            //更改用户密码
            boolean updatePasswordFlag = userDao.updateUserPassword(StudentNumber, newPassword);
            //更改成功，状态为true，返回Boolean值
            return updatePasswordFlag;
        } else {
            return false;
        }

    }

    @Override
    public ArrayList<AreaShow> getAllUseableAreas() {
        ArrayList<AreaShow> areaShows = new ArrayList<>();
        ArrayList<Area> allArea = (ArrayList<Area>) areaDao.getAllArea();
        for (Area areaGet : allArea) {
            //IsUseable只有在y时表明区域可用,对available里的属性值做替换,"y"->"是 "
            if (areaGet.getIsUseable().equals("y")){
                AreaShow areaShow = new AreaShow();
                areaShow.setAreaId(areaGet.getAreaId());
                areaShow.setAreaName(areaGet.getAreaName());
                areaShow.setCapacity(areaGet.getCapacity());
                areaShow.setLocation(areaGet.getLocation());

                if (areaGet.getIsAvailable().equals("y")){
                    areaShow.setIsAvailable("是");
                }else {
                    areaShow.setIsAvailable("否");
                }
                areaShows.add(areaShow);
            }
        }
        System.out.println(areaShows);
        return areaShows;
    }

}
