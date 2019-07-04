import java.io.Serializable;


public class Alarm implements Serializable {
    private String alarmno;
    private String deviceid;
    private String devicename;
    private String ofregionname;
    private String ofsubregionname;
    private String devicemodel;
    private String supplier;
    private String longitude;
    private String dimension;
    private String occurdate;
    private String occurtime;
    private String alarmtype;
    private String alarmdesc;

    public Alarm() {
    }

    public Alarm(String alarmno, String deviceid, String devicename, String ofregionname, String ofsubregionname, String devicemodel, String supplier, String longitude, String dimension, String occurdate, String occurtime, String alarmtype,String alarmdesc) {
        this.alarmno = alarmno;
        this.deviceid = deviceid;
        this.devicename = devicename;
        this.ofregionname = ofregionname;
        this.ofsubregionname = ofsubregionname;
        this.devicemodel = devicemodel;
        this.supplier = supplier;
        this.longitude = longitude;
        this.dimension = dimension;
        this.occurdate = occurdate;
        this.occurtime = occurtime;
        this.alarmtype = alarmtype;
        this.alarmdesc = alarmdesc;
    }

    public String getAlarmno() {
        return alarmno;
    }

    public void setAlarmno(String alarmno) {
        this.alarmno = alarmno;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getOfregionname() {
        return ofregionname;
    }

    public void setOfregionname(String ofregionname) {
        this.ofregionname = ofregionname;
    }

    public String getOfsubregionname() {
        return ofsubregionname;
    }

    public void setOfsubregionname(String ofsubregionname) {
        this.ofsubregionname = ofsubregionname;
    }

    public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getOccurdate() {
        return occurdate;
    }

    public void setOccurdate(String occurdate) {
        this.occurdate = occurdate;
    }

    public String getOccurtime() {
        return occurtime;
    }

    public void setOccurtime(String occurtime) {
        this.occurtime = occurtime;
    }

    public String getAlarmtype() {
        return alarmtype;
    }

    public void setAlarmtype(String alarmtype) {
        this.alarmtype = alarmtype;
    }

    public String getAlarmdesc() {
        return alarmdesc;
    }

    public void setAlarmdesc(String alarmdesc) {
        this.alarmdesc = alarmdesc;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "alarmno='" + alarmno + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", devicename='" + devicename + '\'' +
                ", ofregionname='" + ofregionname + '\'' +
                ", ofsubregionname='" + ofsubregionname + '\'' +
                ", devicemodel='" + devicemodel + '\'' +
                ", supplier='" + supplier + '\'' +
                ", longitude='" + longitude + '\'' +
                ", dimension='" + dimension + '\'' +
                ", occurdate='" + occurdate + '\'' +
                ", occurtime='" + occurtime + '\'' +
                ", alarmtype='" + alarmtype + '\'' +
                ", alarmdesc='" + alarmdesc + '\'' +
                '}';
    }
}
