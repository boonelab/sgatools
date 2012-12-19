package controllers;

public class PlateFile {
	
	public String plateImagePath;
	public String plateImageName;
	public String outputDatPath;
	public String outputDatName;

	public String outputMaskedPath;
	
	public PlateFile(){
		
	}
	public PlateFile(String mainPath, String fileName){
		this.plateImagePath = mainPath;
		this.plateImageName = fileName;
	}
}
