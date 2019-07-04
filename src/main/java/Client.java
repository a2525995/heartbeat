import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Client extends Thread {

    //定义一个Socket对象

    Socket socket = null;
    ArrayList<Alarm> alarmArrayList = new ArrayList<Alarm>();
    AlarmSeralizerMysql alarmSeralizerMysql = new AlarmSeralizerMysql();


    public Client(String host, int port) {
        try {
            //需要服务器的IP地址和端口号，才能获得正确的Socket对象
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        //客户端一连接就可以写数据给服务器了
        new sendMessThread().start();
        new HeartBeatThread().start();

        super.run();
        try {
            // 读取服务器发来的数据
            InputStream s = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(s,"gbk"));
            String line;

            //一次完整的数据包
            StringBuffer message = new StringBuffer();

            while ((line = br.readLine()) != null) {
                message.append(line);
                if(line.contains("</Message>")){
                    //此时的message信息为一次完整的数据
                    String msg = message.toString();
                    //清空数据包
                    message.setLength(0);
                    if(msg.contains("AlarmNO")){
                        //这个数据包是报警信息
                        //获取报警对象
                        Alarm alarm = new AlarmStringToXML(msg).stringToXML();
                        alarmSeralizerMysql.setAlarm(alarm);
                        alarmSeralizerMysql.ExecuteInsert("alarm", alarmSeralizerMysql.AlarmToList());
                        alarmArrayList.add(alarm);
                    }
                }
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器端发送心跳数据线程
     */
    class HeartBeatThread extends Thread{
        @Override
        public void run() {
            super.run();
            //写操作
            try{
                int index = 1;
                while(true){
                    if(index == 30){
                        //发送心跳信息
                        sendHeartBeat();
                        index = 1;
                    }
                    index++;
                    //程序休眠1秒钟
                    Thread.sleep(1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /**
         * 发送心跳包
         */
        void sendHeartBeat(){
            try {
                //通过socket获取字符流           
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gb2312"));

                //获取要传送的xml文件字符串形式
                Date now = new Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
                String dateStr = f.format(now);
                String seqStr = dateStr+"000001";
                HeartBeatXMLToString xmlFile = new HeartBeatXMLToString(seqStr);
                String xml = xmlFile.heartBeatXML();
                System.out.println("hello world!");

                //字符串转换为字符输入流
                InputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                //通过标准输入流获取字符流           
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line +"\n");
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 往服务器端输入数据，需要新开一个线程
     */
    class sendMessThread extends Thread{
        @Override
        public void run() {
            super.run();
            //写操作
            try {
                //通过socket获取字符流           
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gb2312"));

                //获取要传送的xml文件字符串形式
                LoginXMLToString xmlFile = new LoginXMLToString("test", "12345");
                String xml = xmlFile.loginXMLToString();

                //字符串转换为字符输入流
                InputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                //通过标准输入流获取字符流           
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line +"\n");
                    System.out.println(line);
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //函数入口
    public static void main(String[] args) throws IOException{
        //需要服务器的正确的IP地址和端口号
        //连接目标服务器
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        propertiesLoader.loadFile(String.format("%s/%s", System.getProperty("user.dir"), "src/main/resources/server.properties"));
        Client clientTest=new Client(propertiesLoader.getKey("server.host"), Integer.valueOf(propertiesLoader.getKey("server.port")));
        clientTest.start();
    }
}