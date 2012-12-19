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
import ij.process.ImageProcessor;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
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
import views.html.ia.iasummary;
import views.html.ns.*;
import views.html.da.*;


import java.io.*;
import java.nio.charset.Charset;

public class DAcontroller extends Controller {
	//final static Form<NSjob> nsForm = form(NSjob.class);
	
	public static Result initPreloadedDA(String jobid){
		NSjob nsjob = NScontroller.getJobFromJsonFile(jobid);
		return ok(dasummary.render(nsjob));
	}
	
	
	
}

