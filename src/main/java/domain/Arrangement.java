package domain;

public class Arrangement {
    /*area_arrangement.area_id ,area.areaname, area_arrangement.date ,area_arrangement.applyform_id*/
    private String area_id=null;
    private String areaname=null;
    private long date=0;
    private String applyform_id=null;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getApplyform_id() {
        return applyform_id;
    }

    public void setApplyform_id(String applyform_id) {
        this.applyform_id = applyform_id;
    }

    @Override
    public String toString() {
        return "Arrangement{" +
                "area_id='" + area_id + '\'' +
                ", areaname='" + areaname + '\'' +
                ", date='" + date + '\'' +
                ", applyform_id='" + applyform_id + '\'' +
                '}';
    }
}
