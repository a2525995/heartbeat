import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AlarmSeralizerMysql {
    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    private JDBCUtil jdbcUtil;
    private PropertiesLoader loader = PropertiesLoader.getInstance();
    private Alarm alarm;

    public <T> String generateInsertSql(String tableName, List<T> list){
        if (null == alarm){
            System.out.println("Input a Alarm Object Before Operation");
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(tableName);
        sb.append(" values ");
        sb.append("(");
        int index = 1;
        for (T obj: list){

            if (obj instanceof Integer){
                sb.append(obj);
            }
            if (obj instanceof String){
                sb.append('"');
                sb.append(obj);
                sb.append('"');
            }
            if (obj == null){
                sb.append('"');
                sb.append('"');
            }
            if (index != list.size()){
                sb.append(", ");
            }
            index++;
        }
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();

    }

    public List AlarmToList(){
        List list = new ArrayList();
        list.add(alarm.getAlarmno());
        list.add(alarm.getDeviceid());
        list.add(alarm.getDevicename());
        list.add(alarm.getOfregionname());
        list.add(alarm.getOfsubregionname());
        list.add(alarm.getDevicemodel());
        list.add(alarm.getSupplier());
        list.add(alarm.getLongitude());
        list.add(alarm.getDimension());
        list.add(alarm.getOccurdate());
        list.add(alarm.getOccurtime());
        list.add(alarm.getAlarmtype());
        list.add(alarm.getAlarmdesc());
        return list;
    }

    public int ExecuteInsert(String tableName, List list){
        try {
            loader.loadFile(String.format("%s/%s", System.getProperty("user.dir"), "src/main/resources/mysql.properties"));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        jdbcUtil = new JDBCUtil(loader);
        int line = jdbcUtil.executeUpdate(generateInsertSql(tableName, list), null);
        return line;

    }



    public static void main(String[] args) {
        Alarm alarm = new Alarm("1", "1", "testDevice", "testRegion", "testSubRegion", "TestDeviceModel", "testSupplier", "testLongitude", "testDimension", "testOccurdate", "testOccurtime", "testAlarmtype", "testGetAlarmdesc");
        AlarmSeralizerMysql alarmSeralizerMysql = new AlarmSeralizerMysql();
        alarmSeralizerMysql.setAlarm(alarm);
        alarmSeralizerMysql.ExecuteInsert("alarm", alarmSeralizerMysql.AlarmToList());

    }

}
