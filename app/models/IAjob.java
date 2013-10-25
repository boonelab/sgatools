package models;

import java.util.List;

import controllers.PlateFile;

public class IAjob {
    public String jobid;

    // Time elapsed for processing images
    public String timeElapsed;

    // Path where ZIP file created with dat output files
    public String downloadZipPath;// For script output

    // Passed/Failed file objects
    public List<PlateFile> passedFiles, failedFiles;
    public String plateFormat;
    public String cropOption;
    public boolean autoRotate;
    public boolean inverse;
    public String email;

    public String jsonSelectedForNS;

    public IAjob() {
        // Initialize
        this.plateFormat = ComboboxOpts.plateFormat().get(3);
        this.cropOption = ComboboxOpts.cropOption().get(0);

    }

}
