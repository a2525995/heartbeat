import java.awt.peer.SystemTrayPeer;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class JDBCUtil {

    private String driver;
    private String url;
    private String username;
    private String password;

    private CallableStatement callableStatement;
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rst;

    public JDBCUtil(PropertiesLoader propertiesLoader){
        driver = propertiesLoader.getKey("driver");
        url = propertiesLoader.getKey("url");
        username = propertiesLoader.getKey("username");
        password = propertiesLoader.getKey("password");

    }

    public JDBCUtil(String driver, String url, String username, String password){
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection(){
        try {
            try {
                Class.forName(driver);
            }
            catch (ClassNotFoundException e){
                System.out.println("database driver load failed");
                e.printStackTrace();
            }
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    private void Close(){
        //关闭全部
        if (conn != null){
            try {
                conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public int executeUpdate(String sql, Object[] params){
        //only support insert/update/delete
        //affect rows
        int line = 0;
        try {
            conn = getConnection();
            pst = conn.prepareStatement(sql);

            //put params to replace sql '?'
            if (params != null){
                for (int i = 0; i < params.length; i++){
                    pst.setObject(i+1, params[i]);
                }
            }
            line = pst.executeUpdate();
            conn.commit();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Close();
        }
        return line;
    }

    private ResultSet executeQueryRS(String sql, Object[] params){
        try {
            conn = getConnection();

            pst = conn.prepareStatement(sql);

            if (params != null){
                for (int i = 0; i < params.length; i++){
                    pst.setObject(i+1, params[i]);
                }
            }

            rst = pst.executeQuery();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return rst;
    }

    public List<Map> resultSetToList(String sql, Object[] params) throws SQLException{
        ResultSet rs = executeQueryRS(sql, params);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map> list = new ArrayList<Map>();
        while (rs.next()){
            Map map = new HashMap();

            for (int i = 1; i <= columnCount; i++){
                String columnName = metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                //map.put(columnName, value);
                map.put(columnName, value);
            }
            list.add(map);

        }
        return list;
    }


    public static void main(String[] args) throws Exception{
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        try {
            propertiesLoader.loadFile(String.format("%s/%s", System.getProperty("user.dir"), "src/main/resources/mysql.properties"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        JDBCUtil jdbcUtil = new JDBCUtil(propertiesLoader);
        List list = jdbcUtil.resultSetToList("select * from runoob_tbl", null);
        System.out.println(list.get(0));
    }


}
