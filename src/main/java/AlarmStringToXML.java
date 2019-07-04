import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class AlarmStringToXML {

    private String alarmStr;

    public AlarmStringToXML(String alarmStr){
        this.alarmStr = alarmStr;
    }

    /**
     *String类型的报警信息转换为xml格式
     */
    public Alarm stringToXML(){
        Alarm alarm = null;
        try {
            //获取Document对象
            Document document= DocumentHelper.parseText(alarmStr);
            // 获取节点元素对象与值
            Element message = document.getRootElement();//获取根结点

            Element body = message.element("Body");//获取子结点
            Element operation = body.element("Operation");//获取子子结点
            Element alarmInfo = operation.element("AlarmInfo");
            String alarmNo = alarmInfo.element("AlarmNO").getText();//报警编号
            String deviceID = alarmInfo.element("DeviceID").getText();//设备编号
            String deviceName = alarmInfo.element("DeviceName").getText();//设备名称
            String ofRegionName = alarmInfo.element("OfRegionName").getText();//所属地区
            String ofSubregionName = alarmInfo.element("OfSubRegionName").getText();//所属子区
            String deviceModel = alarmInfo.element("DeviceModel").getText();//规格型号
            String supplier = alarmInfo.element("Supplier").getText();//供应商
            String longitude = alarmInfo.element("Longitude").getText();//经度
            String dimension = alarmInfo.element("Dimension").getText();//纬度
            String occurdate = alarmInfo.element("OccurDate").getText();//发生日期
            String occurTime = alarmInfo.element("OccurTime").getText();//发生时间
            String alarmType = alarmInfo.element("AlarmType").getText();//报错类型
            String alarmDesc = alarmInfo.element("AlarmDesc").getText();//报警描述
            //封装报警对象
            alarm = new Alarm(alarmNo,deviceID,deviceName,ofRegionName,ofSubregionName,deviceModel,supplier,longitude,dimension,occurdate,occurTime,alarmType,alarmDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alarm;
    }

    public static void main(String[] args) {
        AlarmStringToXML alarmStringToXML = new AlarmStringToXML("test");
        Alarm alarm = alarmStringToXML.stringToXML();
        System.out.println(alarm.toString());

    }

}
