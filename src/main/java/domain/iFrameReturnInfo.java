package domain;

public class iFrameReturnInfo {
    private String newSrc = null;
    private String errorMsg = null;

    public iFrameReturnInfo() {
    }

    public String getNewSrc() {
        return newSrc;
    }

    public void setNewSrc(String newSrc) {
        this.newSrc = newSrc;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "iFrameReturnInfo{" +
                "newSrc='" + newSrc + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
