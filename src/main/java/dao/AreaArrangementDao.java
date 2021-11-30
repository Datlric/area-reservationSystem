package dao;

import domain.ApplyForm;
import domain.Arrangement;

import java.util.List;

public interface AreaArrangementDao {
    public boolean saveNewArrangement(ApplyForm applyForm);

    public int deleteArrangement(ApplyForm applyForm);

    //检查用户所提交的时间是否与当前已通过的时间有冲突,true代表没有冲突
    public boolean timeConflictCheck(ApplyForm applyForm);

}
