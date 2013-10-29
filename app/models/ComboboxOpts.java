package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComboboxOpts {

    // Image analysis options

    public static List<String> plateFormat() {
        List<String> tmp = new ArrayList<String>();

        tmp.add("96 colonies (8 × 12)");
        tmp.add("384 colonies (16 × 24)");
        tmp.add("768 colonies - diagonal replicates (16 × 24)");
        tmp.add("1536 colonies (32 × 48)");
        return tmp;
    }

    public static List<String> cropOption() {
        List<String> tmp = new ArrayList<String>();

        tmp.add("Automatically choose method (Recommended)");
        tmp.add("Autodetect plate edges");
        tmp.add("Images already cropped to plate edges");
        return tmp;
    }

    // Normalization & Scoring options

    public static List<String> replicates() {
        List<String> tmp = new ArrayList<String>();

        tmp.add("4");
        tmp.add("1");
        return tmp;
    }

    public static List<String> arrayDef() {
        List<String> tmp = new ArrayList<String>();

        tmp.add("― Predefined arrays ―");
        File f = new File(Constants.ARRAY_DEF_PATH);
        for (File array_def : f.listFiles()) {
            if (array_def.isDirectory())
                tmp.add(array_def.getName());
        }
        tmp.add("― Upload custom ―");
        return tmp;
    }

    public static List<String> arrayDefPlates(String arrayDefinitionFile) {
        List<String> tmp = new ArrayList<String>();
        tmp.add("All plates");
        File f = new File(Constants.ARRAY_DEF_PATH + "/" + arrayDefinitionFile);
        for (File plate : f.listFiles()) {
            if (!plate.getName().startsWith(".")) {
                tmp.add(plate.getName());
            }
        }
        
        // Sort in a natural order
        Collections.sort(tmp, new NaturalOrderComparator());
        return tmp;
    }

    public static List<String> scoringFunctions() {
        List<String> tmp = new ArrayList<String>();

        tmp.add("C&#7522;&#11388; - C&#7522;C&#11388;");
        tmp.add("C&#7522;&#11388; / (C&#7522;C&#11388;)");
        return tmp;
    }

    // Data analysis options
    // NA

}
