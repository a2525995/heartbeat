import sun.security.jca.GetInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static PropertiesLoader instance;

    private static Properties properties = new Properties();

    public String getKey(String key) {
        return properties.getProperty(key);
    }

    private PropertiesLoader(){

    }

    public static PropertiesLoader getInstance() {
        if (instance == null) {
            synchronized (PropertiesLoader.class) {
                if (instance == null) {
                    instance = new PropertiesLoader();
                }
            }
        }
        return instance;
    }

    public void loadFile(String filePath) throws IOException {

        InputStream inputStream = new FileInputStream(new File(filePath));
        properties.load(inputStream);
    }

}
