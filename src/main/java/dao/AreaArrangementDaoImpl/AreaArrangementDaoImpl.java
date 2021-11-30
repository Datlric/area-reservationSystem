package dao.AreaArrangementDaoImpl;

import dao.AreaArrangementDao;
import domain.ApplyForm;
import domain.Arrangement;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.ArrangementSetUtil;
import util.JdbcUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AreaArrangementDaoImpl implements AreaArrangementDao {

    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public boolean saveNewArrangement(ApplyForm applyForm) {
        Set<Date> dateSet = ArrangementSetUtil.getArrangementDates(applyForm.getStartTime(), applyForm.getEndTime());
        String sql = "insert into area_arrangement(area_id,date,applyform_id,isPass) values (?,?,?,'y')";
        String sql2 = "select * from area_arrangement where (date >= ? and date <= ?)";

        try {
            if (dateSet != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //将dateSet里的日期数据依次插入进数据库中
                for (Date date : dateSet) {
                    template.update(sql, applyForm.getAreaId(), date.getTime(), applyForm.getApplyFormId());
                }
                //检查插入天数长度是否一致
                long startTime = (simpleDateFormat.parse(applyForm.getStartTime()).getTime());
                long endTime = (simpleDateFormat.parse(applyForm.getEndTime()).getTime());
                List<Map<String, Object>> list = template.queryForList(sql2, startTime, endTime);
                return dateSet.size() == list.size();

            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int deleteArrangement(ApplyForm applyForm) {

        String sql = "delete from area_arrangement where (date>=? and date<=?) and applyform_id=?";

        try {
            if (applyForm != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                long startTime = simpleDateFormat.parse(applyForm.getStartTime()).getTime();
                long endTime = simpleDateFormat.parse(applyForm.getEndTime()).getTime();
                //根据开始与结束时间将dateSet里的日期数据删除
                int update = template.update(sql, startTime, endTime, applyForm.getApplyFormId());
                if (update > 0) {
                    return 1;
                } else if (update == 0) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public boolean timeConflictCheck(ApplyForm applyForm) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "select * from area_arrangement where (date between ? and ?) and area_id=?";
        try {
            long startTime = simpleDateFormat.parse(applyForm.getStartTime()).getTime();
            long endTime = simpleDateFormat.parse(applyForm.getEndTime()).getTime();
            List<Map<String, Object>> list = template.queryForList(sql, startTime, endTime, applyForm.getAreaId());
            return list.size() == 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

}
