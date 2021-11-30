package domain;

public class ApplyForm {
    private String applyFormId = null;
    private String subTime = null;
    private String startTime = null;
    private String endTime = null;
    private String reason = null;
    private String areaId = null;
    private String studentNumber = null;
    private String isPass = null;
    private String passTime = null;

    public ApplyForm() {
    }

    public String getApplyFormId() {
        return applyFormId;
    }

    public void setApplyFormId(String applyFormId) {
        this.applyFormId = applyFormId;
    }

    public String getSubTime() {
        return subTime;
    }

    public void setSubTime(String subTime) {
        this.subTime = subTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getIsPass() {
        return isPass;
    }

    public void setIsPass(String isPass) {
        this.isPass = isPass;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    @Override
    public String toString() {
        return "applyForm{" +
                "applyFormId='" + applyFormId + '\'' +
                ", subTime='" + subTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", reason='" + reason + '\'' +
                ", areaId='" + areaId + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", isPass='" + isPass + '\'' +
                ", passTime='" + passTime + '\'' +
                '}';
    }
}
