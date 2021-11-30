package domain;

public class AreaShow {
   private String areaId = null;
   private String areaName = null;
   private int    capacity = 0;
   private String location = null;
   private String isAvailable = null;

    public AreaShow() {
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
        return "AreaShow{" +
                "areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", capacity=" + capacity +
                ", location='" + location + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                '}';
    }

    public void reset(){
        this.areaId = null;
        this.areaName = null;
        this.capacity = 0;
        this.location = null;
        this.isAvailable = null;
    }
}
