package controllers;


import play.Logger;
import play.libs.Akka;
import play.libs.F.*;
import play.libs.Json;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;


import ij.ImagePlus;
import ij.io.FileSaver;
import ij.plugin.ContrastEnhancer;
import ij.plugin.JpegWriter;
import ij.plugin.frame.ContrastAdjuster;
import ij.process.ImageProcessor;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import colonyMeasure.ColonyGrid;
import colonyMeasure.ImageMeasurer;
import colonyMeasure.ImageMeasurerOptions;

import com.google.common.io.Files;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.*;
import views.html.ia.*;


import java.io.*;
import java.nio.charset.Charset;

public class IAcontroller extends Controller {
	final static Form<IAjob> ipForm = form(IAjob.class);
	final static Form<NSjob> nsForm = form(NSjob.class);
	
	public static Result initIAForm(){
		Form<IAjob> newform = ipForm.fill(new IAjob());
		return ok(iaform.render(newform));
	}
	
	public static IAjob getJobFromJsonFile(String jobid){
		try{
			String jsonString = Help.readFile(Constants.JOB_OUTPUT_DIR + File.separator + 
					jobid +File.separator +"ia"+ File.separator +
					jobid + ".json");
			IAjob ipJob = Json.fromJson(Json.parse(jsonString), IAjob.class);
			
			return ipJob;
		}catch(Exception e){
			Logger.error(e.getMessage());
			return null;
		}
	}
	
	public static boolean writeJsonJobToFile(IAjob job, String jobid){
		try{
			JsonNode jn = Json.toJson(job);
			Help.writeFile(jn.toString(), 
					Constants.JOB_OUTPUT_DIR + File.separator + 
					jobid +File.separator +"ia"+ File.separator +
					jobid + ".json");
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Result showJob(String jobid){
		IAjob ipJob  = IAcontroller.
			getJobFromJsonFile( jobid );
		
		if(ipJob == null) { return ok(errorpage.render("The job you have requested was not found, please check to make your job ID is correct", "404")); }
		
		return ok(iasummary.render(ipJob, nsForm.fill(new NSjob())));
	}
	
	public static Result submit(){
		//Process submission
		long submissionStartTime = new Date().getTime(); //start time
		
		//Get form
		Form<IAjob> filledForm = ipForm.bindFromRequest();
		IAjob ipJob = filledForm.get();
		
		//Get file(s) uploaded
		MultipartFormData body = request().body().asMultipartFormData();
		List<FilePart> plateImages = body.getFiles();
		
        if(plateImages.isEmpty()){
        	filledForm.reject("plateImages", "Please select at least one plate image");
        }
        
        //If any errors arise, reject the form submission
        if (filledForm.hasErrors()) { return badRequest(iaform.render(filledForm)); }
        
        //Output directory: if it does not exist, create it. If we fail to create it halt
        File outDir = new File(Constants.JOB_OUTPUT_DIR);
        if(!outDir.exists()){
        	boolean res = outDir.mkdir();
        	if(!res){ 
            	filledForm.reject("plateImages", "Fatal error: failed to process images, please report this");
            	//return badRequest(iaform.render(filledForm)); 
        	}
        }
        
        //Before processing, set some stuff
        String jobid = UUID.randomUUID().toString();
        
        String outputDir = Constants.JOB_OUTPUT_DIR + File.separator + jobid + File.separator;
        
        Logger.info("outputDir="+outputDir);
        
        String iaDir = outputDir + "ia" + File.separator;
        String inputImagesDir = iaDir + "input_images"+	File.separator;
        String outputImagesDir = iaDir + "output_images"+	File.separator;
        String outputFilesDir = iaDir + "output_files"+	File.separator;
        
        boolean res1 = (new File(outputDir)).mkdir();
        boolean res2 = (new File(iaDir)).mkdir();
        boolean res3 = (new File(inputImagesDir)).mkdir();
        boolean res4 = (new File(outputImagesDir)).mkdir();
        boolean res5 = (new File(outputFilesDir)).mkdir();

        String zipFilePath = iaDir+ "imageanalysis-sgatools-"+jobid+".zip";
        
        // Set parameters for HT colony grid analyzer
        Integer gridType = ComboboxOpts.plateFormat().indexOf(ipJob.plateFormat);
        Integer cropType = Integer.parseInt("1"+ComboboxOpts.cropOption().indexOf(ipJob.cropOption));
        Integer showOption = ImageMeasurer.SHOW_NOTHING;
        if(gridType == 3) gridType = ColonyGrid.GRID_1536;
        
        // Lists of passed/failed images to be filled
        List<PlateFile> passedPlateImages = new ArrayList();
        List<PlateFile> failedPlateImages = new ArrayList();
        
        // Loop through images and process one at a time
        for(FilePart fp: plateImages){
        	//Get path and name
        	String plateImageName = fp.getFilename();
        	String plateImagePath = fp.getFile().getPath(); 
        	
        	//Save input images 
        	File inputImageDest = new File(inputImagesDir + plateImageName);
        	try {
				FileUtils.copyFile(fp.getFile(), inputImageDest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return TODO;
			}
        	
        	//Create new plate image object
        	PlateFile pi = new PlateFile(inputImageDest.getPath(), plateImageName);
        	
        	try{
        		//Do image processing here
                //Load in the image
                ImagePlus im = new ImagePlus(plateImagePath);
                
                //ContrastEnhancer enh = new ContrastEnhancer();
                //enh.stretchHistogram(im, 10);
                //FileSaver sss = new FileSaver(im);
                //sss.saveAsJpeg("/Users/omarwagih/Desktop/adjustedContrast.jpg");
                
                ImageProcessor ip = im.getProcessor();
               	
//                for(int i=1; i<20; i++){
//                	ImagePlus cimage = new ImagePlus(plateImagePath);
//                	enh = new ContrastEnhancer();
//                    int factor = (5*i);
//                    //enh.stretchHistogram(image, factor);
//                    try{
//                    		enh.stretchHistogram(cimage, factor);
//                    	 sss = new FileSaver(cimage);
//                         sss.saveAsJpeg("/Users/omarwagih/Desktop/contrast/contrast"+factor+".jpg");
//                    }catch(Exception e){
//                    	Logger.error("FAILED CONTRAST ADJUSTER:"+e.getMessage());
//                    }
//                   
//                }
               
                
                //Run image measurer and save .dat file
                String datFileName = plateImageName + ".dat";
                String datOutputPath = outputFilesDir + datFileName;
                ImageMeasurerOptions imo=new ImageMeasurerOptions(gridType, cropType, showOption);
                ImageMeasurer imM=new ImageMeasurer(ip,datOutputPath,imo);
                //Save masked out image
                String maskedImgOutputPath = outputImagesDir + "masked_"+plateImageName;
                ImagePlus maskedImage = imM.grid.maskedImage;
                FileSaver fs = new FileSaver(maskedImage);
                
                JpegWriter.setQuality(20);
                fs.saveAsJpeg(maskedImgOutputPath);
                //ContrastEnhancer.
                
                //Store for viewing after
                pi.outputMaskedPath =  maskedImgOutputPath.replace(Constants.BASE_PUBLIC_DIR, "");
                pi.outputDatPath =  datOutputPath.replace(Constants.BASE_PUBLIC_DIR, "");
                pi.outputDatName =  datFileName;
                
                //Add to passed list
                passedPlateImages.add(pi);
                //End processing
        	}catch(Exception e){
        		//Add to failed list
                failedPlateImages.add(pi);
        	}
        	
        }
        
		//If none of the input files passed, reject form
        if(passedPlateImages.isEmpty()){
        	filledForm.reject("plateImages", "Failed to process all images, please ensure the correct plate images/format is selected");
        	return badRequest(iaform.render(filledForm));
        }
        
        //Create zip
        try{
            Zipper.zipDir(zipFilePath, outputFilesDir);
        }catch(Exception e){
            Logger.error("Failed to ZIP\n"+e.getMessage());
        }
        
        		
        //Record time elapsed
        long submissionEndTime = new Date().getTime(); //end time
        long milliseconds = submissionEndTime - submissionStartTime; //check different
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) %	60);
        
        ipJob.timeElapsed =  minutes + " mins "+ seconds + " secs";
        ipJob.jobid = jobid;
        ipJob.passedFiles = passedPlateImages;
        ipJob.failedFiles = failedPlateImages;
        ipJob.downloadZipPath = zipFilePath.replace(Constants.BASE_PUBLIC_DIR, "");
        
		//Write job as a json - used to show job page with link again
		boolean writePassed = writeJsonJobToFile(ipJob, jobid);
		if(!writePassed){
			filledForm.reject("plateImages", "Fatal error, please contact developers");
        	return badRequest(iaform.render(filledForm));
		}
		
		//Direct to summary page
		return redirect("/imageanalysis/"+jobid);
	}
	
	
	
}

