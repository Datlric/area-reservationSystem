package service;

import domain.ApplyForm;
import domain.User;

import java.util.List;

public interface ApplyFormService {
    //保存新的用户预约表
    public boolean saveApplyForm(ApplyForm applyForm);

    //检查传入的用户号当前是否已存在预约
    public boolean checkUserReserveIsExist(User user);

    //根据用户学号查询其预约单信息
    public ApplyForm getApplyFormByStudentNumber(String studentNumber);

    //根据用户学号删除其预约单信息
    public boolean deleteApplyFormByStudentNumber(String studentNumber);

    //检查开始与结束时间是否间隔超过一周
    public boolean timeCrossedCheck(String startTime, String endTime);

    //根据”ispass“状态获取对应的所有预约单
    public List<ApplyForm> getAllFormByStatus(String isPass);

    //根据多个”ispass“状态获取对应的所有预约单
    public List<ApplyForm> getAllFormByMutStatus(String status1,String status2);

    //根据applyFormId及对应方法参数名称设置申请单的状态
    public boolean setApplyFormStatus(String applyFormId,String method);

    //检查用户所填写的时间是否与现有已通过的预约单有冲突
    public boolean reserveTimeConflictCheck(ApplyForm applyForm);
}
