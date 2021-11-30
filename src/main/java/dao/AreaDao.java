package dao;

import domain.Area;

import java.util.List;

public interface AreaDao {
    //获取所有活动区域的信息，返回一个list
    public List<Area> getAllArea();

    //根据AreaId返回区域信息
    public Area getAreaByAreaId(String AreaId);

    //根据areaId删除area信息
    public int delAreaByAreaId(String AreaId);
}
