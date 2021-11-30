package dao.AreaDaoImpl;

import dao.AreaDao;
import domain.Area;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JdbcUtils;

import java.util.List;

public class AreaDaoImpl implements AreaDao {
    private JdbcTemplate template = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public List<Area> getAllArea() {
        String sql = "select * from area";

        try {
            List<Area> allArea = template.query(sql, new BeanPropertyRowMapper<Area>(Area.class));
            return allArea;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Area getAreaByAreaId(String areaId) {
        String sql = "select * from area where areaid=?";

        try {
            Area area = template.queryForObject(sql, new BeanPropertyRowMapper<Area>(Area.class), areaId);
            if (area != null) {
                return area;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int delAreaByAreaId(String AreaId) {
        String sql = "delete from area where areaid=?";
        String sql2 = "delete from area_arrangement where area_id=?";

        try {
            int update1 = template.update(sql, AreaId);
            int update2 = template.update(sql2, AreaId);

            if (update1 > 0 && update2 >= 0) {
                return 1;
            } else if (update1 == 0 && update2 == 0) {
                return 0;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


}
