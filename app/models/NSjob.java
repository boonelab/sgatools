package models;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.validation.*;

import play.data.validation.Constraints.*;
public class NSjob {

	//General variables
	public String jobid;
	public String timeElapsed;
	public String outputPath;//For script output
	public Map<String, File> plateFilesMap;
	
	//Specific to normalization
	public Boolean doArrayDef;
	public String arrayDefPredefined;
	public String selectedArrayDefPlate;
	public String summaryAD;
	
	public Integer replicates;
	public Boolean doLinkage;
	public String linkageCutoff;
	public String linkageGenes;
	
	//Specific to scoring
	public Boolean doScoring;
	public String scoringFunction;
	
	public String downloadZipPath;
	
	public String jsonSelectedForNS;
	public Map<String, String> preloadedPlateMap;
	
	public Map<String, String> outputFilesMap;
	
	public NSjob(){
		//Initialize
		this.doArrayDef = false;
		this.arrayDefPredefined = ComboboxOpts.arrayDef().get(0);
		this.selectedArrayDefPlate = "";
		
		this.replicates = 4;
		this.doLinkage = false;
		this.linkageCutoff = "200";
		this.linkageGenes = "CAN1, LYP1";
		
		this.doScoring = false;
		this.scoringFunction = ComboboxOpts.scoringFunctions().get(1);
	}
	
}
