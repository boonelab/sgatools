package controllers;


import play.Logger;

import java.io.*;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;


import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.da.*;


public class DAcontroller extends Controller {
	final static Form<DAjob> daform = form(DAjob.class);
	
	public static Result initPreloadedDA(String jobid){
		NSjob nsjob = NScontroller.getJobFromJsonFile(jobid);
		Form<DAjob> dajob = daform.fill(new DAjob());
		
		return ok(dasummary.render(nsjob, dajob));
	}
	
	public static Result submit(){	
		String path = System.getProperty("java.io.tmpdir")+"download.svg";
		Form<DAjob> filledForm = daform.bindFromRequest();
		
		DAjob job = filledForm.get();
//		if(!job.saveType.equals("SVG")){
//			return ok(job.saveType + " generation is not yet implemented");
//		}
		
		Logger.debug("GRAPHICS_PATH="+path);
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(path));
			out.write(job.inputHmap);
			out.close();
		}catch (IOException e){
			return TODO;		
		}
		
		File svgFile = new java.io.File(path);
		
		if(!job.saveType.equals("SVG")){
			//Convert the SVG into PDF
			File outputFile = null;
			try {
				outputFile = File.createTempFile("result-", "."+job.saveType.toLowerCase());
				SVGConverter converter = new SVGConverter();
				if(job.saveType.equals("PDF"))
					converter.setDestinationType(DestinationType.PDF);
				if(job.saveType.equals("PNG"))
					converter.setDestinationType(DestinationType.PNG);
				converter.setWidth(1000);
				converter.setSources(new String[] { svgFile.toString() });
				converter.setDst(outputFile);
				converter.execute();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.info("FAILED");
			}
			return ok(outputFile);
					//.as("image/x-png");
		}
		
		return ok(new java.io.File(path));
	}
	
	public static void test(){
		
		
		
		
	}
}

