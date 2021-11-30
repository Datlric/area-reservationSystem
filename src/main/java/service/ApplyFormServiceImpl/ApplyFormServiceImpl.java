package service.ApplyFormServiceImpl;

import dao.ApplyFormDao;
import dao.ApplyFormDaoImpl.ApplyFormDaoImpl;
import dao.AreaArrangementDao;
import dao.AreaArrangementDaoImpl.AreaArrangementDaoImpl;
import domain.ApplyForm;
import domain.User;
import service.ApplyFormService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ApplyFormServiceImpl implements ApplyFormService {

    ApplyFormDao applyFormDao = new ApplyFormDaoImpl();
    AreaArrangementDao areaArrangementDao = new AreaArrangementDaoImpl();

    @Override
    public boolean saveApplyForm(ApplyForm applyForm) {
        //设置提交时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subTime = formatter.format(calendar.getTime());
        subTime = subTime.replaceAll(" ", ";");
        applyForm.setSubTime(subTime);

        //applyFormId为区域号+#+学号+#+提交时间
        String applyFormId = applyForm.getAreaId() + "#" + applyForm.getStudentNumber() + "#" + applyForm.getSubTime();
        applyForm.setApplyFormId(applyFormId);

        //设置审核状态IsPass为正在处理
        applyForm.setIsPass("processing");

        boolean saveFlag = applyFormDao.saveNewApplyForm(applyForm);

        return saveFlag;
    }

    @Override
    public boolean checkUserReserveIsExist(User user) {
        boolean checkFlag = applyFormDao.checkReserveIsExist(user);
        return checkFlag;
    }


    @Override
    public ApplyForm getApplyFormByStudentNumber(String studentNumber) {
        ApplyForm applyForm = applyFormDao.getApplyFormByStudentNumber(studentNumber);
        return applyForm;
    }

    @Override
    public boolean deleteApplyFormByStudentNumber(String studentNumber) {
        ApplyForm applyForm = applyFormDao.getApplyFormByStudentNumber(studentNumber);
        if ("y".equals(applyForm.getIsPass())) {
            //用户注销其预约单的时候顺便把安排表里面的时间安排删除掉
            int i = areaArrangementDao.deleteArrangement(applyForm);
            boolean delFlag = applyFormDao.deleteApplyFormByStudentNumber(studentNumber);
            return delFlag && (i == 1);
        }else {
            boolean delFlag = applyFormDao.deleteApplyFormByStudentNumber(studentNumber);
            return delFlag;
        }
    }

    @Override
    public boolean timeCrossedCheck(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date sc = simpleDateFormat.parse(startTime);
            Date ec = simpleDateFormat.parse(endTime);

            if ((ec.getTime() - sc.getTime()) / (1000 * 60 * 60 * 24) > 6) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<ApplyForm> getAllFormByStatus(String isPass) {
        List<ApplyForm> applyForms = applyFormDao.getAllFormByStatus(isPass);
        return applyForms;
    }

    @Override
    public List<ApplyForm> getAllFormByMutStatus(String status1, String status2) {
        List<ApplyForm> applyForms = applyFormDao.getAllFormByMutStatus(status1, status2);
        return applyForms;
    }

    @Override
    public boolean setApplyFormStatus(String applyFormId, String method) {
        ApplyForm applyForm = applyFormDao.getApplyFormByApplyFormId(applyFormId);
        if ("pass".equals(method)) {
            boolean setYFlag = applyFormDao.setApplyFormY(applyFormId);
            //todo 11.10这里去写日程表数据库里相关的内容
            boolean saveNewArrangementFlag = areaArrangementDao.saveNewArrangement(applyForm);
            return setYFlag && saveNewArrangementFlag;
        } else if ("deny".equals(method)) {
            boolean setNFlag = applyFormDao.setApplyFormN(applyFormId);
            return setNFlag;
        } else if ("processing".equals(method)) {
            boolean setPFlag = applyFormDao.setApplyFormProcessing(applyFormId);
            int i = areaArrangementDao.deleteArrangement(applyForm);
            return setPFlag && (i == 1 || i == 0);
        } else {
            return false;
        }
    }

    @Override
    public boolean reserveTimeConflictCheck(ApplyForm applyForm) {
        boolean conflictCheck = areaArrangementDao.timeConflictCheck(applyForm);
        return conflictCheck;
    }


}
