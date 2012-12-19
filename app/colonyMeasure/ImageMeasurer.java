/*
 * Created on Mar 6, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package colonyMeasure;

/**
 * @author Sean
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * 11-11-2009 started working on adding color measurements but did not finish
 */

import java.util.Arrays;
import java.util.Collections;

import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.measure.ResultsTable;
import ij.measure.ResultsTablePlus;
import ij.plugin.filter.ImageTools;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.filter.RGBStackSplitterSean;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import ij.plugin.filter.PixelMean;
import ij.plugin.frame.RoiManager;

import java.awt.Rectangle;
import java.io.*;

public class ImageMeasurer {
	
	public static int SHOW_NOTHING=0;
	static int SHOW_GRID_OVERLAY=1;
	
	double PLATE_EDGE_HEIGHT_FRAC;
	double PLATE_EDGE_WIDTH_FRAC;
	int MINIMUM_PLATE_HEIGHT;
	int MINIMUM_PLATE_WIDTH;
	
	//Image cropping details
	int cropBot,cropTop,cropL,cropR;

	ResultsTablePlus rtTotal,rtF,rt2;
	public ColonyGrid grid;
	ColonyGrid orig_grid;
	int ERROR_FLAG;
	String filename, flagString;
	boolean errorFlagBool;
	MeasuredResults imageResults;
	
	public ImageMeasurer(ImageProcessor ip, String fn, ImageMeasurerOptions imo) throws IOException {
		int j;
		MeasuredResults testResults;
		
		filename=fn;
		ERROR_FLAG=0;	// Default - nothing went wrong
		System.out.println("ImageMeasurer: "+filename);
		//System.out.println("Java memory in use = " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		ImagePlus im=new ImagePlus("temp",ip);
		
		ImageProcessor red=ip;
		//ImageProcessor blue,green;
		if (im.getBitDepth()==24) {
			red=getRedChannel(ip);
			//green=getGreenChannel(ip);
			//blue=getBlueChannel(ip);
		} else {
			red=ip.convertToByte(false);
		}

		ImagePlus red1=new ImagePlus("Red channel",red);
		ImagePlus redTemp=new ImagePlus("Red channel",red);  //used to figure out how to rotate the image
		im.flush();  //attempting to minimize memory wastage
		setCroppingParameters(imo.gridType);

		redTemp = doImageCropping(imo, redTemp);
		int thresh = getColonyThreshold(redTemp);
		//System.out.println(thresh);
		rtTotal=getParticles(redTemp,thresh,50,5000);
		createColonyGrid(imo, redTemp);  //sets the value of grid
		//redTemp.show();

		redTemp.flush(); //attempting to minimize memory wastage

		// *** Rotate the image to try to account for the grid slant, and then set up the grid again
		red1=new ImagePlus("Red channel",performEstimatedRotation(red1,grid.slope));
		redTemp=red1;  //redTemp will store the uncropped image in case we need it later
		red1 = doImageCropping(imo, red1);
		thresh = getColonyThreshold(red1);
		rtTotal=getParticles(red1,thresh,50,5000);
		createColonyGrid(imo, red1);  //sets the value of grid

		// *** Continue with analysis
		grid.applyGridMask(red1);
		rtF=getParticles(grid.maskedImage,thresh,10,5000);
		imageResults=new MeasuredResults(grid,rtF);
		orig_grid=grid;

		for (j=0;j<2;j++) {
			ERROR_FLAG=0;
			checkBasicErrors();		// sets errorFlagBool and flagString
			if (errorFlagBool) { //Take a second shot at it
				System.out.println("Taking a another try at the image - "+flagString);
				ERROR_FLAG=1; //if it gets here twice, it will report an error, even though it might still process the image correctly
				if (imageResults.gridExceedsImageBounds) {
					red1 = remeasureWithExpandedGrid(red1, redTemp, thresh);
				}
				switch (imo.gridType) {
					case ColonyGrid.GRID_96: grid=new ColonyGrid96(red1,rtTotal,orig_grid,j); break;
					case ColonyGrid.GRID_384: grid=new ColonyGrid384(red1,rtTotal,orig_grid,j); break;
					case ColonyGrid.GRID_768a: grid=new ColonyGrid768a(red1,rtTotal,orig_grid,j); break;
					case ColonyGrid.GRID_1536: grid=new ColonyGrid1536(red1,rtTotal,orig_grid,j); break;
				}
				grid.applyGridMask(red1);
				rtF=getParticles(grid.maskedImage,thresh,10,5000);
				testResults=new MeasuredResults(grid,rtF);
				if (testResults.isBetterThan(imageResults)) imageResults=testResults;
			}
			//System.out.println("Left bound = " + grid.leftBound);
			//System.out.println("Right bound = " + grid.rightBound);
			//System.out.println("Half space = " + grid.halfSpace);
		}
		imageResults.exportResults(filename);
		if (imageResults.inconsistentReplicates()) {
			ERROR_FLAG=ColMeasureProgram.ERR_REPLICATES;
		}
		if ((rtF.numRows<(grid.numCol*grid.numRow*grid.numDup*0.75)) | (imageResults.numzero>(0.33*grid.numCol*grid.numRow*grid.numDup))) {
			System.out.println("too few colonies");
			ERROR_FLAG=ColMeasureProgram.ERR_TOO_FEW_COL;
		}
		if (((rtF.numRows>(grid.numCol*grid.numRow*grid.numDup*2)) | (imageResults.typicalNumSpots>1.1))) {
			System.out.println("too many colonies");
			ERROR_FLAG=ColMeasureProgram.ERR_TOO_MANY_COL;
		}
		if ((red1.getHeight()-grid.gridHeight>grid.heightTol)) {
			System.out.println("height tolerance error "+(red1.getHeight()-grid.gridHeight));
			ERROR_FLAG=ColMeasureProgram.ERR_CROP_ERROR;
		}
		if (imageResults.gridExceedsImageBounds) {
			System.out.println("Cropping error - bounds of grid exceeded bounds of image");
			ERROR_FLAG=ColMeasureProgram.ERR_CROP_ERROR;
		}
		if ((rtF.numRows<(grid.numCol*grid.numRow*grid.numDup*0.75))|(rtF.numRows>(grid.numCol*grid.numRow*grid.numDup*2))|(red1.getHeight()-grid.gridHeight>grid.heightTol) | (imageResults.typicalNumSpots>1.1) | imageResults.gridExceedsImageBounds | imageResults.inconsistentReplicates()) {
			if (ERROR_FLAG<1) ERROR_FLAG=1;
			System.out.println("Error processing file");
		}
		if (imo.showOption==SHOW_GRID_OVERLAY) {
			imageResults.grid.maskedImage.show();
		}
	}

	private void checkBasicErrors() {
		errorFlagBool=((imageResults.typicalNumSpots>1.1)) | (imageResults.numzero>(0.33*grid.numCol*grid.numRow*grid.numDup)) | (imageResults.gridExceedsImageBounds);
		if ((imageResults.typicalNumSpots>1.1)) {
			flagString="typical num spots too high";
		}
		if (imageResults.numzero>(0.33*grid.numCol*grid.numRow*grid.numDup)) {
			flagString="too many zeros";
		}
		if (imageResults.gridExceedsImageBounds) {
			flagString="grid exceeds image bounds";
		}
	}

	private ImagePlus remeasureWithExpandedGrid(ImagePlus red1, ImagePlus redTemp, int thresh) {
		System.out.println("Original grid exceeded original cropped image - enlarging image and trying again");
		int numExtraPixels=(int)Math.round(red1.getHeight()*0.025);
		red1=generateLargerCroppedImage(redTemp,numExtraPixels);
		//grid=imageResults.grid;  //Retrieve the best grid we've had so far
		//grid.adjustGridPositionForExpandedImage(numExtraPixels);
		//grid.applyGridMask(red1);
		//rtF=getParticles(grid.maskedImage,thresh,10,5000);
		//imageResults=new MeasuredResults(grid,rtF);
		return red1;
	}

	private void createColonyGrid(ImageMeasurerOptions imo, ImagePlus redTemp) throws IOException {
		switch (imo.gridType) {
			case ColonyGrid.GRID_96: grid=new ColonyGrid96(redTemp,rtTotal); break;
			case ColonyGrid.GRID_384: grid=new ColonyGrid384(redTemp,rtTotal); break;
			case ColonyGrid.GRID_768a: grid=new ColonyGrid768a(redTemp,rtTotal); break;
			case ColonyGrid.GRID_1536: grid=new ColonyGrid1536(redTemp,rtTotal); break;
		}
	}

	private ImagePlus doImageCropping(ImageMeasurerOptions imo, ImagePlus redTemp) {
		if ((imo.cropType==ColonyGrid.AUTO_CROP_OPTION)|(imo.cropType==ColonyGrid.AUTO_CROP_DECIDE_OPTION)) {
			redTemp=new ImagePlus("Red channel",cropOutsidePlate(redTemp,imo));
		} else {
			redTemp=new ImagePlus("Red channel",justCropPlateEdges(redTemp));
		}
		return redTemp;
	}
	
	/**
	private void measureAndExportFromMasked(ColonyGrid grid) {  //This method is no longer used
		BufferedWriter bw;
		FileWriter fwriter;
		String line;
		File outFile;
		String sc=File.separator;
		OvalRoi roi;
		int inds[];
		double top, left, area,circ,areas[],circs[];
		int numSpots[];
		int i,j,k,m,count;
		outFile=new File(filename);
		areas=new double[grid.numDup];
		circs=new double[grid.numDup];
		numSpots=new int[grid.numDup*grid.numCol*grid.numRow];
		try {
			bw = new BufferedWriter(new FileWriter(outFile));
			line="row  column";
			for (k=0;k<grid.numDup;k++) {
				line=line+"  size-"+(k+1);
			}
			for (k=0;k<grid.numDup;k++) {
				line=line+"  circ-"+(k+1);
			}
			writeln(bw,line);
			int tot=0;
			count=0;
			//numzero=0;
			for (i=0;i<grid.numRow;i++) {
				for (j=0;j<grid.numCol;j++) {
					line=(i+1)+" "+(j+1);
					for (k=0;k<grid.numDup;k++) {
						area=0; circ=0;
						roi=grid.getBoundingOvalForScoring(j,i,k);
						inds=findSpotsInOval(rtF,numF,roi);
						numSpots[count]=inds.length;
						count=count+1;
						tot=tot+inds.length;
						for (m=0;m<inds.length;m++) {
							if (rtF.getValue("Area",inds[m])>area) {
								if (spotIsNearCenter(rtF,inds[m],roi)) {
									area=rtF.getValue("Area",inds[m]);
									circ=rtF.getValue("Circ.",inds[m]);
								} else {
									//System.out.println(line+" "+(k+1)+" Spot not near center "+rtF.getValue("Area",inds[m]));
								}
							}
						}
						if (area==0) numzero=numzero+1;
						areas[k]=area;
						line=line+" "+Math.round(area);
						circs[k]=((double)Math.round(circ*1000))/1000;
					}
					for (k=0;k<grid.numDup;k++) {
						line=line+ " " +circs[k];
					}				
					writeln(bw,line);
				}
			}
			bw.close();
			Arrays.sort(numSpots);
			int ind1=Math.round(numSpots.length/2);
			int ind2=Math.round(numSpots.length*3/4);
			typicalNumSpots=((double)partialSum(numSpots,ind1,ind2))/((double)(ind2-ind1+1));
			//System.out.println("tot = "+tot);
		}
		catch (FileNotFoundException fnf) {
			System.err.println("Could not find the file");
		}
		catch (IOException io) {
			System.err.println("Error reading the file");
		}		
	}  **/
	
	protected static boolean spotIsNearCenter(ResultsTablePlus rt, int ind, OvalRoi roi) {
		int x=(int)Math.round(rt.getValue("X",ind));
		int y=(int)Math.round(rt.getValue("Y",ind));
		Rectangle r=roi.getBounds();
		double diameter=r.width;
		double radius=r.width*7/20; //the center of the colony should be inside a smaller oval
		double centX=r.x+diameter/2;
		double centY=r.y+diameter/2;
		boolean res = (distance(x,y,centX,centY)<radius);
		return res;
	}
	
	protected static double distance(double x1, double y1, double x2, double y2) {
		double distance=(x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
		distance=Math.sqrt(distance);
		return distance;
	}
			
	private void writeln(BufferedWriter bw, String str) {
		try {
			bw.write(str, 0, str.length());
			bw.newLine();
		}
		catch (IOException io) {
			System.out.println("Error writing a line to the file");
		}
	}
	private ImageProcessor getRedChannel(ImageProcessor ip) {
		RGBStackSplitterSean splitter=new RGBStackSplitterSean();
		splitter.split(new ImagePlus("tempImage",ip));
		ImagePlus red=new ImagePlus("tempImage",splitter.red);
		return red.getProcessor();
	}
	private ImageProcessor getGreenChannel(ImageProcessor ip) {
		RGBStackSplitterSean splitter=new RGBStackSplitterSean();
		splitter.split(new ImagePlus("tempImage",ip));
		ImagePlus red=new ImagePlus("tempImage",splitter.green);
		return red.getProcessor();
	}
	private ImageProcessor getBlueChannel(ImageProcessor ip) {
		RGBStackSplitterSean splitter=new RGBStackSplitterSean();
		splitter.split(new ImagePlus("tempImage",ip));
		ImagePlus red=new ImagePlus("tempImage",splitter.blue);
		return red.getProcessor();
	}
	
	protected static ResultsTablePlus getParticles(ImagePlus im, int thresh, int minSize, int maxSize) {
		int i,j,k,m, left,top,width,height;
		int pixList[],temp;
		
		ImagePlus im2=ImageTools.threshold(im,thresh);
		im2.setRoi(0,0,im2.getWidth(),im2.getHeight());
		//im2.show();
		ResultsTablePlus rt = new ResultsTablePlus();
		int options=0;
		int measurements=ParticleAnalyzer.AREA + ParticleAnalyzer.CENTROID+ParticleAnalyzer.ELLIPSE+ParticleAnalyzer.CIRCULARITY+ParticleAnalyzer.MEDIAN;//+ParticleAnalyzer.SHOW_OUTLINES;
		ParticleAnalyzer pa=new ParticleAnalyzer(options,measurements,rt,minSize,maxSize);
		pa.analyze(im2);
		rt.numRows=rt.getCounter();
		//System.out.println("got here - "+rt.numRows);
		for (i=0;i<rt.numRows;i++) {
			width=5; height=5;
			left=(int)Math.round(rt.getValue("X", i))-2;
			top=(int)Math.round(rt.getValue("Y", i))-2;
			pixList=new int[width*height];
			m=0;
			for (j=0;j<width;j++) {
				for (k=0;k<height;k++) {
					temp=im.getProcessor().getPixel(left+j, top+k);
					//System.out.println(temp);
					pixList[m]=temp;
					m++;
				}
			}
			//System.out.println("medval = " + computeMedian(pixList));
			rt.setValue("Median", i, computeMedian(pixList));
		}
		//System.out.println("got here");
		
		return rt;
	}
	
	protected static int[] findSpotsInOval(ResultsTablePlus rt, OvalRoi roi) {
		int list1[]=new int[rt.numRows];
		int x,y;
		int i,j=0;
		Rectangle r=roi.getBoundingRect();
		for (i=0;i<rt.numRows;i++) {
			x=(int)Math.round(rt.getValue("X",i));
			y=(int)Math.round(rt.getValue("Y",i));
			if (roi.contains(x,y)) {
				list1[j]=i;
				j=j+1;
			}
		}
		int list[]=new int[j];
		for (i=0;i<j;i++) {
			list[i]=list1[i];
		}
		return list;
	}
	
	public static int getColonyThreshold(ImagePlus im) {
		int h=im.getHeight();
		int w=im.getWidth();
		im.setRoi(w/4,h/4,w/2,h/2);
		ImagePlus im2=ImageTools.copy(im,im.getRoi());
		ImageProcessor ip=im2.getProcessor();
		return ((ByteProcessor)ip).getAutoThreshold();
	}
	
	protected static double getBaselinePixelValue(ImagePlus im) {
		int h=im.getHeight();
		int w=im.getWidth();
		double val1[] = new double[4];
		
		val1[0]=PixelMean.get(im,0,1,w,2);
		val1[1]=PixelMean.get(im,0,h-3,w,2);
		val1[2]=PixelMean.get(im,1,0,2,h);
		val1[3]=PixelMean.get(im,w-3,0,2,h);
		Arrays.sort(val1);
		
		return (val1[1]+val1[2])/2;
	}
	protected static int partialSum(int[] list, int start, int stop) {
		int i;
		int tot=0;
		if (start<0) {
			start=0;
		}
		for (i=start;i<=stop;i++) {
			tot=tot+list[i];
		}
		return tot;
	}
	protected static int computeMedian(int[] list) {
		Arrays.sort(list);
		int ind1=list.length/2;	//this value will automatically be rounded down
		int med;
		if (list.length==2*ind1) {
			med = (list[ind1]+list[ind1-1])/2;
		} else {
			med = list[ind1];
		}
		return med;
	}
	
	private ImageProcessor cropOutsidePlate(ImagePlus im, ImageMeasurerOptions imo) {
		double med=0;
		int h=im.getHeight();
		int w=im.getWidth();

		double baseline=getBaselinePixelValue(im);
		cropBot = findVertCropPos(im, h-1, -1, baseline);
		cropTop = findVertCropPos(im, 0, 1, baseline);
		int tempFullHeight = cropBot-cropTop;
		cropBot=cropBot-(int)Math.round(PLATE_EDGE_HEIGHT_FRAC*tempFullHeight);
		cropTop=cropTop+(int)Math.round(PLATE_EDGE_HEIGHT_FRAC*tempFullHeight);
		if ((cropBot-cropTop<MINIMUM_PLATE_HEIGHT)&(imo.cropType==ColonyGrid.AUTO_CROP_DECIDE_OPTION)) {
			System.out.println(cropBot-cropTop + " minimum plate height");
			cropTop=(int)Math.round(PLATE_EDGE_HEIGHT_FRAC*h);
			cropBot=h-(int)Math.round(PLATE_EDGE_HEIGHT_FRAC*h);
		}
		cropL = findHorCropPos(im,0,1, baseline);
		cropR = findHorCropPos(im,w-1,-1, baseline);
		int tempFullWidth=cropR-cropL;
		cropL = cropL + (int)Math.round(PLATE_EDGE_WIDTH_FRAC*tempFullWidth);
		cropR = cropR - (int)Math.round(PLATE_EDGE_WIDTH_FRAC*tempFullWidth);
		if ((cropR-cropL<MINIMUM_PLATE_WIDTH)&(imo.cropType==ColonyGrid.AUTO_CROP_DECIDE_OPTION)) {
			cropL=(int)Math.round(w*PLATE_EDGE_WIDTH_FRAC);
			cropR=w-(int)Math.round(w*PLATE_EDGE_WIDTH_FRAC);
		}
		im.setRoi(cropL,cropTop,cropR-cropL,cropBot-cropTop);
		return im.getProcessor().crop();
	}
	private ImagePlus generateLargerCroppedImage(ImagePlus im, int numExtraPixels) {
		cropL=cropL-numExtraPixels;
		cropR=cropR+numExtraPixels;
		cropTop=cropTop-numExtraPixels;
		cropBot=cropBot+numExtraPixels;
		im.setRoi(cropL,cropTop,cropR-cropL,cropBot-cropTop);
		return new ImagePlus("Red channel",im.getProcessor().crop());
	}
	private ImageProcessor justCropPlateEdges(ImagePlus im) {
		double med=0;
		int h=im.getHeight();
		int w=im.getWidth();
		int cropBot,cropTop,cropL,cropR;

		cropBot = h;
		cropTop = 0;
		int tempFullHeight = cropBot-cropTop;
		cropBot=cropBot-(int)Math.round(PLATE_EDGE_HEIGHT_FRAC*tempFullHeight);
		cropTop=cropTop+(int)Math.round(PLATE_EDGE_HEIGHT_FRAC*tempFullHeight);
		cropL = 0;
		cropR = w;
		int tempFullWidth=cropR-cropL;
		cropL = cropL + (int)Math.round(PLATE_EDGE_WIDTH_FRAC*tempFullWidth);
		cropR = cropR - (int)Math.round(PLATE_EDGE_WIDTH_FRAC*tempFullWidth);
		im.setRoi(cropL,cropTop,cropR-cropL,cropBot-cropTop);
		return im.getProcessor().crop();
	}
	private ImageProcessor performEstimatedRotation(ImagePlus im, double slope) {
		ImageProcessor res;
		int h=im.getHeight();
		int w=im.getWidth();
		//System.out.println("h="+h+" w="+w);
		double angle=Math.atan(slope)/Math.PI*-180;
		double angle2=Math.abs(angle);
		double frac=2*Math.sin(angle2*Math.PI/180);
		//int cropTop=(int)Math.round(h*0.025);
		//int cropH=(int)Math.round(h*0.95);
		//int cropL=(int)Math.round(w*0.025);
		//int cropW=(int)Math.round(w*0.95);
		int cropTop=(int)Math.round(h*frac);
		int cropH=(int)Math.round(h*(1-2*frac));
		int cropL=(int)Math.round(w*frac);
		int cropW=(int)Math.round(w*(1-2*frac));
		//System.out.println(angle+" "+angle2+"  "+frac);
		
		res=im.getProcessor();
		res.rotate(angle);
		res.setRoi(cropL, cropTop, cropW, cropH);
		System.out.println("Rotating image by "+angle+" degrees");
		return res.crop();
	}

	protected static int findVertCropPos(ImagePlus im, int start,int dir, double baseline) {
		double med;
		int w=im.getWidth();
		int h=im.getHeight();
		int i;
		int[] medValues;
		int top=h/2 - 10;
		int[] tops;
		double typical=PixelMean.get(im,0,top,w,top+20);
		double thresh=(typical+baseline)/2;
		//System.out.println("baseline: "+baseline+" "+typical+" "+thresh);
		medValues=new int[Math.round(h/3)+1];
		tops=new int[Math.round(h/3)+1];
		for (i=0;i<h/4;i++) {
			top=start+i*1*dir+(dir-1);
			tops[i]=top;
			med=PixelMean.get(im,0,top,w,2);
			if (med>thresh) {
				medValues[i]=1;
			} else {
				medValues[i]=0;
			}
		}
		i=i-2;
		int flag=1;
		while (flag>0) {
			if (partialSum(medValues,i-9,i) == 0) {
				flag=0;
			}
			i=i-1;
		}
		i=tops[i];
		//System.out.println("Found border at "+i);
		return i;
	}
	protected static int findHorCropPos(ImagePlus im, int start,int dir, double baseline) {
		double med;
		int w=im.getWidth();
		int h=im.getHeight();
		int i;
		int[] medValues;
		int right=w/2 - 10;
		int[] rights;
		double typical=PixelMean.get(im,right,0,20,h);
		double thresh=(typical+baseline)/2;
		medValues=new int[Math.round(w/4)+1];
		rights=new int[Math.round(w/4)+1];
		for (i=0;i<h/4;i++) {
			right=start+i*1*dir+(dir-1);
			rights[i]=right;
			med=PixelMean.get(im,right,0,2,h);
			if (med>thresh) {
				medValues[i]=1;
			} else {
				medValues[i]=0;
			}
		}
		i=i-2;
		int flag=1;
		while ((flag>0) & (i>8)) {
			if (partialSum(medValues,i-9,i) == 0) {
				flag=0;
			}
			i=i-1;
		}
		i=rights[i];
		return i;
	}
	
	private void setCroppingParameters(int gridType) {
		switch (gridType) {
			case ColonyGrid.GRID_96:
			PLATE_EDGE_HEIGHT_FRAC=ColonyGrid96.PLATE_EDGE_HEIGHT_FRAC;
			PLATE_EDGE_WIDTH_FRAC=ColonyGrid96.PLATE_EDGE_WIDTH_FRAC;
			MINIMUM_PLATE_HEIGHT=ColonyGrid96.MINIMUM_PLATE_HEIGHT;
			MINIMUM_PLATE_WIDTH=ColonyGrid96.MINIMUM_PLATE_WIDTH;
			break;
			case ColonyGrid.GRID_384:
			PLATE_EDGE_HEIGHT_FRAC=ColonyGrid384.PLATE_EDGE_HEIGHT_FRAC;
			PLATE_EDGE_WIDTH_FRAC=ColonyGrid384.PLATE_EDGE_WIDTH_FRAC;
			MINIMUM_PLATE_HEIGHT=ColonyGrid384.MINIMUM_PLATE_HEIGHT;
			MINIMUM_PLATE_WIDTH=ColonyGrid384.MINIMUM_PLATE_WIDTH;
			break;
			case ColonyGrid.GRID_768a:
			PLATE_EDGE_HEIGHT_FRAC=ColonyGrid768a.PLATE_EDGE_HEIGHT_FRAC;
			PLATE_EDGE_WIDTH_FRAC=ColonyGrid768a.PLATE_EDGE_WIDTH_FRAC;
			MINIMUM_PLATE_HEIGHT=ColonyGrid768a.MINIMUM_PLATE_HEIGHT;
			MINIMUM_PLATE_WIDTH=ColonyGrid768a.MINIMUM_PLATE_WIDTH;
			break;
		}
	}

}
