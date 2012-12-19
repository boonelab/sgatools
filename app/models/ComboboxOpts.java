package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.Constants;

import java.io.*;

import javax.swing.JOptionPane;

public class ComboboxOpts {
	
	// Image analysis options

	public static List<String> plateFormat(){
		List<String> tmp = new ArrayList();

		tmp.add("96 colonies");
		tmp.add("384 colonies");
		tmp.add("768 colonies - diagonal replicates");
		tmp.add("1536 colonies");
		return tmp;
	}
	
	public static List<String> cropOption(){
		List<String> tmp = new ArrayList();

		tmp.add("Automatically choose method (Recommended)");
		tmp.add("Autodetect plate edges");
		tmp.add("Images already cropped to plate edges");
		return tmp;
	}
	
	
	// Normalization & Scoring options
	
	public static List<String> replicates(){
		List<String> tmp = new ArrayList();

		tmp.add("4");
		tmp.add("1");
		return tmp;
	}
	
	public static List<String> arrayDef(){
		List<String> tmp = new ArrayList();

		tmp.add("― Predefined arrays ―");
		File f = new File(Constants.ARRAY_DEF_PATH);
		for(File array_def: f.listFiles()){
			if(array_def.isDirectory())
				tmp.add(array_def.getName());
		}
		tmp.add("― Upload custom ―");
		return tmp;
	}
	
	public static List<String> arrayDefPlates(String arrayDefinitionFile){
		List<String> tmp = new ArrayList();
		tmp.add("All plates");
		File f = new File(Constants.ARRAY_DEF_PATH+"/"+arrayDefinitionFile);
		for(File plate: f.listFiles()){
			if(!plate.getName().startsWith(".")){
				tmp.add(plate.getName());
			}
		}
		//Sort in a natural order
		Collections.sort(tmp, new NaturalOrderComparator());
		return tmp;
	}
	
	public static List<String> scoringFunctions(){
		List<String> tmp = new ArrayList();

		tmp.add("C&#7522;&#11388; - C&#7522;C&#11388;");
		tmp.add("C&#7522;&#11388; / (C&#7522;C&#11388;)");
		return tmp;
	}

	// Data analysis options
	// NA
	
}
