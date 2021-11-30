package domain;

import java.util.ArrayList;

public class ArrangementShow {
    private String areaId=null;
    private String areaName=null;
    private ArrayList<String> date=null;

    public ArrangementShow() {
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ArrangementShow{" +
                "areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", date=" + date +
                '}';
    }
}
