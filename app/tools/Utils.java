package tools;

import java.io.File;

import com.google.common.base.Joiner;

public class Utils {
    public static String joinPath(String ... elements) {
        return Joiner.on(File.separator).join(elements);
    }
}
