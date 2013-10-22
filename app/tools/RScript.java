package tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import models.Constants;
import play.Logger;

public class RScript {
    public enum RScriptType {
        SGATOOLS(Constants.RSCRIPT_PATH), GITTER(Constants.GITTER_PATH);

        public String path;

        private RScriptType(String path) {
            this.path = path;
        }
    }

    private List<String> args = new ArrayList<String>();

    public RScript(RScriptType type) {
        args.add("Rscript");
        args.add(type.path);
    }

    public RScript addOpt(String opt) {
        args.add(opt.startsWith("--") ? opt : "--" + opt);
        return this;
    }

    public RScript addOpt(String opt, String val) {
        addOpt(opt);
        args.add(val);
        return this;
    }

    public boolean execute() {
        Logger.info("Running script: " + Joiner.on(" ").join(args));
        
        StringBuilder shellOutput = new StringBuilder();
        StringBuilder shellOutputError = new StringBuilder();
        String line;
        
        try {
            // Try execute and read
            Process p = Runtime.getRuntime().exec(args.toArray(new String[0]));

            // Read in output returned from shell
            // #########FOR DEBUG ONLY######
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = in.readLine()) != null) {
                shellOutput.append(line + "\n");
            }

            // Read in error input stream (if we have some error)
            BufferedReader inError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = inError.readLine()) != null) {
                shellOutputError.append(line + "\n");
            }
            inError.close();
            
            // #############################
            Logger.debug(shellOutput.toString());
            if (!shellOutputError.toString().isEmpty()) {
                Logger.error(shellOutputError.toString());
            }
        } catch (Exception e) {
            Logger.error(shellOutput.toString());
            Logger.error("===============================");
            Logger.error(shellOutputError.toString());
            
            return false;
        }
        
        return true;
    }
}
