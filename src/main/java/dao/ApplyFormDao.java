package dao;

import domain.ApplyForm;
import domain.User;

import java.util.List;

public interface ApplyFormDao {
    //保存新的预约单
    public boolean saveNewApplyForm(ApplyForm newApplyForm);

    //根据用户学号查找是否存在预约
    public boolean checkReserveIsExist(User user);

    //根据学号查询预约单信息,根据学号
    public ApplyForm getApplyFormByStudentNumber(String studentNumber);

    //根据applyFormId获取预约单的所有信息
    public ApplyForm getApplyFormByApplyFormId(String ApplyFormId);

    //根据用户学号删除其预约单信息
    public boolean deleteApplyFormByStudentNumber(String studentNumber);

    //根据areaId、startTime、endTime检查在安排表中是否有时间冲突
    public boolean checkConflict(String areaId, String startTime, String endTime);

    //根据”ispass“状态获取对应的所有预约单
    public List<ApplyForm> getAllFormByStatus(String isPass);

    public List<ApplyForm> getAllFormByMutStatus(String status1,String status2);

    //根据applyFormId设置其状态为y
    public boolean setApplyFormY(String applyFormId);

    //根据applyFormId设置其状态为n
    public boolean setApplyFormN(String applyFormId);

    //根据applyFormId设置其状态为processing
    public boolean setApplyFormProcessing(String applyFormId);
}
