package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import models.ComboboxOpts;
import models.Constants;
import models.IAjob;
import models.NSjob;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonNode;

import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import tools.RScript;
import tools.RScript.RScriptType;
import tools.Utils;
import views.html.errorpage;
import views.html.ia.iaform;
import views.html.ia.iasummary;

import com.google.common.base.Joiner;

public class IAcontroller extends Controller {
    final static Form<IAjob> ipForm = form(IAjob.class);
    final static Form<NSjob> nsForm = form(NSjob.class);

    public static Result initIAForm() {
        Form<IAjob> newform = ipForm.fill(new IAjob());
        return ok(iaform.render(newform));
    }

    public static IAjob getJobFromJsonFile(String jobid) {
        try {
            String jsonString = Help.readFile(Utils.joinPath(Constants.JOB_OUTPUT_DIR, jobid, "ia", jobid + ".json"));
            IAjob ipJob = Json.fromJson(Json.parse(jsonString), IAjob.class);

            return ipJob;
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return null;
        }
    }

    public static boolean writeJsonJobToFile(IAjob job, String jobid) {
        try {
            JsonNode jn = Json.toJson(job);
            Help.writeFile(jn.toString(), Utils.joinPath(Constants.JOB_OUTPUT_DIR, jobid, "ia", jobid + ".json"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Result showJob(String jobid) {
        IAjob ipJob = IAcontroller.getJobFromJsonFile(jobid);

        if (ipJob == null) {
            return ok(errorpage.render(
                    "The job you have requested was not found, please check to make your job ID is correct", "404"));
        }

        return ok(iasummary.render(ipJob, nsForm.fill(new NSjob())));
    }

    public static Result submit() {
        // Process submission
        long submissionStartTime = new Date().getTime(); // start time

        // Get form
        Form<IAjob> filledForm = ipForm.bindFromRequest();
        IAjob ipJob = filledForm.get();

        // Get file(s) uploaded
        MultipartFormData body = request().body().asMultipartFormData();
        List<FilePart> plateImages = body.getFiles();

        if (plateImages.isEmpty()) {
            filledForm.reject("plateImages", "Please select at least one plate image");
        }

        // If any errors arise, reject the form submission
        if (filledForm.hasErrors()) {
            return badRequest(iaform.render(filledForm));
        }

        // Output directory: if it does not exist, create it. If we fail to
        // create it halt
        File outDir = new File(Constants.JOB_OUTPUT_DIR);
        if (!outDir.exists()) {
            boolean res = outDir.mkdir();
            if (!res) {
                filledForm.reject("plateImages", "Fatal error: failed to process images, please report this");
                // return badRequest(iaform.render(filledForm));
            }
        }

        // Before processing, set some stuff
        String jobid = UUID.randomUUID().toString();

        String outputDir = Utils.joinPath(Constants.JOB_OUTPUT_DIR, jobid);

        Logger.info("outputDir=" + outputDir);

        String iaDir = Utils.joinPath(outputDir, "ia");
        String inputImagesDir = Utils.joinPath(iaDir, "input_images");
        String outputImagesDir = Utils.joinPath(iaDir, "output_images");
        String outputFilesDir = Utils.joinPath(iaDir, "output_files");

        Utils.mkdir(outputDir);
        Utils.mkdir(iaDir);
        Utils.mkdir(inputImagesDir);
        Utils.mkdir(outputImagesDir);
        Utils.mkdir(outputFilesDir);

        String zipFilePath = Utils.joinPath(iaDir, "imageanalysis-sgatools-" + jobid + ".zip");

        // Set parameters for HT colony grid analyzer
        Integer gridType = ComboboxOpts.plateFormat().indexOf(ipJob.plateFormat);

        // Lists of passed/failed images to be filled
        List<PlateFile> passedPlateImages = new ArrayList<PlateFile>();
        List<PlateFile> failedPlateImages = new ArrayList<PlateFile>();
        List<String> inputFiles = new ArrayList<String>();
        List<String> inputFileNames = new ArrayList<String>();
        
        Map<String, PlateFile> inputFileMap = new HashMap<String, PlateFile>();
        
        // Loop through images and process one at a time
        for (FilePart fp : plateImages) {
            // Get path and name
            String plateImageName = fp.getFilename();
            
            // Save input images
            File inputImageDest = new File(Utils.joinPath(inputImagesDir, plateImageName));
            try {
                FileUtils.copyFile(fp.getFile(), inputImageDest);
            } catch (IOException e) {
                e.printStackTrace();
                return TODO;
            }

            // Create new plate image object
            PlateFile pi = new PlateFile(inputImageDest.getPath(), plateImageName);
            pi.outputMaskedPath = Utils.joinPath(outputImagesDir, "gridded_" + pi.plateImageName).replace(Constants.BASE_PUBLIC_DIR, "");
            pi.outputDatPath = Utils.joinPath(outputFilesDir, pi.plateImageName + ".dat").replace(Constants.BASE_PUBLIC_DIR, "");
            pi.outputDatName = pi.plateImageName + ".dat";
            
            inputFiles.add(pi.plateImagePath);
            inputFileNames.add(pi.plateImageName);
            inputFileMap.put(pi.plateImageName, pi);
        }
        
        RScript imageProcessing = new RScript(RScriptType.GITTER);
        imageProcessing.addOpt("inputfiles", Joiner.on(",").join(inputFiles)
                ).addOpt("plateformat", getPlateFormatValue(gridType)
                ).addOpt("gridfolder", outputImagesDir
                ).addOpt("datfolder", outputFilesDir);
        
        if (ipJob.removeNoise) imageProcessing.addOpt("removenoise");
        if (ipJob.autoRotate) imageProcessing.addOpt("autorotate");
        if (ipJob.inverse) imageProcessing.addOpt("inverse");
        
        boolean processingResult = imageProcessing.execute();
        
        if (!processingResult) {
            filledForm.reject("plateImages",
                    "Failed to run image processing on uploaded images, please contact developers");
            return badRequest(iaform.render(filledForm));
        }
        
        for (File f : new File(inputImagesDir).listFiles()) {
            if (f.getName().startsWith("gitter_failed_images")) {
                try {
                    String content = Help.readFile(f.getPath());
                    
                    for (String line : content.split("\n")) {
                        if (line.startsWith("#")) continue;
                        
                        if (inputFileMap.containsKey(line)) {
                            failedPlateImages.add(inputFileMap.get(line));
                            inputFileMap.remove(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        passedPlateImages.addAll(inputFileMap.values());
        
        // If none of the input files passed, reject form
        if (passedPlateImages.isEmpty()) {
            filledForm.reject("plateImages",
                    "Failed to process all images, please ensure the correct plate images/format is selected");
            return badRequest(iaform.render(filledForm));
        }
        
        for (PlateFile pf : passedPlateImages) {
            // TODO: Remove this when Omar fixes it in gitter
            String tmpPath = Constants.BASE_PUBLIC_DIR + pf.outputDatPath;
            try {
                String dat = Help.readFile(tmpPath);
                if (!dat.startsWith("#"))
                    Help.writeFile("#" + Help.readFile(tmpPath), tmpPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // Create zip
        try {
            Zipper.zipDir(zipFilePath, outputFilesDir);
        } catch (Exception e) {
            Logger.error("Failed to ZIP\n" + e.getMessage());
        }

        // Record time elapsed
        long submissionEndTime = new Date().getTime(); // end time
        long milliseconds = submissionEndTime - submissionStartTime; // check
                                                                     // different
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);

        ipJob.timeElapsed = minutes + " mins " + seconds + " secs";
        ipJob.jobid = jobid;
        ipJob.passedFiles = passedPlateImages;
        ipJob.failedFiles = failedPlateImages;
        ipJob.downloadZipPath = zipFilePath.replace(Constants.BASE_PUBLIC_DIR, "");

        // Write job as a json - used to show job page with link again
        boolean writePassed = writeJsonJobToFile(ipJob, jobid);
        if (!writePassed) {
            filledForm.reject("plateImages", "Fatal error, please contact developers");
            return badRequest(iaform.render(filledForm));
        }

        // Direct to summary page
        return redirect("/imageanalysis/" + jobid);
    }
    
    private static String getPlateFormatValue(int gridType) {
        switch (gridType) {
        case 0: return "8,12";
        case 1: return "16,24";
        case 2: return "16,24";
        case 3: return "32,48";
        }
        return "";
    }
}
