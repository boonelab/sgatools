package tools;

import java.io.File;

import play.Logger;

import com.google.common.base.Joiner;

public class Utils {
    public static String joinPath(String ... elements) {
        return Joiner.on(File.separator).join(elements);
    }
    
    public static void mkdir(String dir) {
        File d = new File(dir);
        if (d.mkdirs()) {
            Logger.info(String.format("Created directory %s", dir));
        } else {
            Logger.error(String.format("Failed to create directory %s", dir));
        }
    }
}
