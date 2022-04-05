package projectUtils.processors;

public class RunProcessor {

    public static EnvironmentTypes getEnvironment() {
        String env = PropertiesProcessor.getPropertyByKey(PropertyTypes.RUN_PROPS, "test.environment");
        if (env.equals("prod"))
            //Not supported at this time
            return null;
        else
            return EnvironmentTypes.STAGING;
    }

    public static String getTestUrl() {
            return PropertiesProcessor.getPropertyByKey(PropertyTypes.LOCAL_PROPS, "test.url");
    }

}
