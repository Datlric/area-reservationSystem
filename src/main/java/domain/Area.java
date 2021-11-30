package domain;

import java.util.Map;

public class Area {
    private String areaId = null;
    private String areaName = null;
    private int capacity = 0;
    private String isUseable = null;
    private String location = null;
    private String isAvailable = null;

    public Area() {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getIsUseable() {
        return isUseable;
    }

    public void setIsUseable(String isUseable) {
        this.isUseable = isUseable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", capacity=" + capacity +
                ", isUseable='" + isUseable + '\'' +
                ", location='" + location + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }
}
