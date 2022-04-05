package projectUtils.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProcessor {

    //Constants:
    private static final File RUN_PROPERTIES_FILE = new File("./src/test/resources/propertyFiles/run.properties");
    private static final  File LOCAL_PROPERTIES_FILE = new File("./src/test/resources/propertyFiles/local.properties");


    public static String getPropertyByKey(PropertyTypes propertyType, String key) {
        try {
            InputStream propFile = new FileInputStream(LOCAL_PROPERTIES_FILE);
            switch (propertyType) {
                case RUN_PROPS:
                    propFile = new FileInputStream(RUN_PROPERTIES_FILE);
                    break;
                case LOCAL_PROPS:
                    propFile = new FileInputStream(LOCAL_PROPERTIES_FILE);
                    break;
            }
            Properties prop = new Properties();
            prop.load(propFile);
            return prop.getProperty(key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
