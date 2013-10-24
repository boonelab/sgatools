package models;

import java.io.IOException;
import java.util.Properties;

public class Constants {
    public final static String SGATOOLS_VERSION = "1.0.0";
    public final static String IA_VERSION = "1.1.7";

    public static final Properties prop;
    static {
        prop = new Properties();
        try {
            prop.load(Constants.class.getClassLoader().getResourceAsStream("local.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can't locate local properties file, check conf/ folder");
            System.exit(-1);
        }
    }

    public final static String BASE = prop.getProperty("sgatools.base.path", "");

    public final static String BASE_PUBLIC_DIR = BASE + "/public";

    public final static String RSCRIPT_DIR = BASE_PUBLIC_DIR + "/SGAtools";
    public final static String RSCRIPT_PATH = RSCRIPT_DIR + "/WebSGAtools.R";

    public final static String GITTER_DIR = BASE_PUBLIC_DIR + "/gitter";
    public final static String GITTER_PATH = GITTER_DIR + "/gitter.R";
    
    public final static String JOB_OUTPUT_DIR = BASE_PUBLIC_DIR + "/jobs";

    public final static String ARRAY_DEF_PATH = BASE_PUBLIC_DIR + "/data/array-definitions";

}
