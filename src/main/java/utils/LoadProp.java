package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProp {

    private static final Properties prop = new Properties();
    private static final String testDataPath = "/src/test/java/qumu/TestData/TestData.properties";
    private static final File currentDirectory = new File(new File("").getAbsolutePath());

    static {
        try (FileInputStream input = new FileInputStream(currentDirectory + testDataPath)) {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
