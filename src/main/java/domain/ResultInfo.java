package domain;

public class ResultInfo {
    /*
      用于封装后端返回前端数据对象
     */
    private boolean flag; //后端返回结果正常为true，发生异常返回false
    private Object data=null; //后端返回的数据对象
    private String errorMsg=null; //发生异常的错误信息

    //无参构造
    public ResultInfo() {
    }

    /**
     * 有参构造方法
     * @param flag
     */
    public ResultInfo(boolean flag) {
        this.flag = flag;
    }

    /**
     * 有参构造方法
     * @param flag
     * @param data
     */
    public ResultInfo(boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    /**
     * 有参构造方法
     * @param flag
     * @param data
     * @param errorMsg
     */
    public ResultInfo(boolean flag, Object data, String errorMsg) {
        this.flag = flag;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "flag=" + flag +
                ", data=" + data +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
