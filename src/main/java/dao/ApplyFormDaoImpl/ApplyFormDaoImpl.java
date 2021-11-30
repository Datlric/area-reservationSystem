package dao.ApplyFormDaoImpl;

import dao.ApplyFormDao;
import domain.ApplyForm;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JdbcUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ApplyFormDaoImpl implements ApplyFormDao {
    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public boolean saveNewApplyForm(ApplyForm newApplyForm) {
        String sql = "insert into applyform(applyformid, subtime, starttime, endtime, reason, areaid, studentnumber, ispass) values (?,?,?,?,?,?,?,?)";
        try {
            int flag = template.update(sql,
                    newApplyForm.getApplyFormId(),
                    newApplyForm.getSubTime(),
                    newApplyForm.getStartTime(),
                    newApplyForm.getEndTime(),
                    newApplyForm.getReason(),
                    newApplyForm.getAreaId(),
                    newApplyForm.getStudentNumber(),
                    newApplyForm.getIsPass());

            return flag != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkReserveIsExist(User user) {
        String sql = "select COUNT('applyformid') from applyform where studentnumber=?";
        long queryUserSelf = 0;
        try {
            Map<String, Object> map = template.queryForMap(sql, user.getStudentnumber());
            queryUserSelf = (long) map.get("COUNT('applyformid')");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (queryUserSelf != 0) {
            //表示当前用户有预约
            return true;
        } else {
            //表示当前用户无预约
            return false;
        }

    }

    @Override
    public ApplyForm getApplyFormByStudentNumber(String studentNumber) {
        String sql = "select * from applyform where studentnumber=?";
        try {
            ApplyForm applyForm = template.queryForObject(sql, new BeanPropertyRowMapper<ApplyForm>(ApplyForm.class), studentNumber);
            return applyForm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ApplyForm getApplyFormByApplyFormId(String ApplyFormId) {
        String sql = "select * from applyform where applyformid=?";
        try {
            ApplyForm applyForm = template.queryForObject(sql, new BeanPropertyRowMapper<ApplyForm>(ApplyForm.class), ApplyFormId);
            if (applyForm!=null){
                return applyForm;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteApplyFormByStudentNumber(String studentNumber) {
        String sql = "DELETE FROM applyform WHERE studentnumber = ?";

        try {
            int rows = template.update(sql, studentNumber);
            if (rows != 0) {
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
    public boolean checkConflict(String areaId, String userStartTime, String userEndTime) {
        String sql = "select * from applyform where areaid = ? and ispass = 'y' and  ((starttime between ? and ?) or (endtime between ? and ?))";

        return false;
    }

    @Override
    public List<ApplyForm> getAllFormByStatus(String isPass) {
        String sql = "select * from applyform where ispass=?";
        try {
            List<ApplyForm> applyForms = template.query(sql, new BeanPropertyRowMapper<ApplyForm>(ApplyForm.class), isPass);
            return applyForms;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ApplyForm> getAllFormByMutStatus(String status1, String status2) {

        String sql = "select * from applyform where ispass=? or ispass=?";
        try {
            List<ApplyForm> applyForms = template.query(sql, new BeanPropertyRowMapper<ApplyForm>(ApplyForm.class), status1, status2);
            return applyForms;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setApplyFormY(String applyFormId) {
        String sql = "update applyform set ispass='y',passtime=? where applyformid=?";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = dateFormat.format(date);
        try {
            int i = template.update(sql, currentTime, applyFormId);
            if (i != 0) {
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
    public boolean setApplyFormN(String applyFormId) {
        String sql = "update applyform set ispass='n',passtime=? where applyformid=?";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = dateFormat.format(date);
        try {
            int i = template.update(sql, currentTime, applyFormId);
            if (i != 0) {
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
    public boolean setApplyFormProcessing(String applyFormId) {
        String sql = "update applyform set ispass='processing',passtime='' where applyformid=?";
        try {
            int i = template.update(sql, applyFormId);
            if (i != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
