package colonyMeasure;

import ij.gui.OvalRoi;

import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import models.Constants;

import ij.measure.ResultsTablePlus;
import ij.ImagePlus;

public class MeasuredResults {
	int numCol,numRow;		//number of columns and rows in the grid
	int numDup;				//number of duplicates of each colony.
	int numzero;			//stores the number of spots with a measured colony size of zero
	double typicalNumSpots;	//stores the typical number of spots found in a grid location (used as a diagnostic for correct alignment of the grid)
	double typicalNumSpotsNearCent;	//stores the typical number of spots found close to the center of a grid location (used as a diagnostic for correct alignment of the grid)
	//double topRowFrac;		//stores the fraction of top row spots with a colony
	double area[][][], circ[][][], medInt[][][];	//these store the measurements
	ColonyGrid grid;
	boolean gridExceedsImageBounds;
	
	public MeasuredResults(ColonyGrid grid0, ResultsTablePlus rt) {
		grid=grid0;
		numCol=grid.numCol;
		numRow=grid.numRow;
		numDup=grid.numDup;
		area=new double[numRow][numCol][numDup];
		circ=new double[numRow][numCol][numDup];
		medInt=new double[numRow][numCol][numDup];
		gridExceedsImageBounds=grid.checkIfGridExceedsBoundsOfImage();
		
		measureImage(grid, rt);
	}
	
	private void measureImage(ColonyGrid grid, ResultsTablePlus rt) {
		OvalRoi roi;
		int inds[];
		double area1,circ1,medInt1;
		int numSpots[], numSpotsCent[];
		int i,j,k,m,count, numNearCent;
		numSpots=new int[grid.numDup*grid.numCol*grid.numRow];
		numSpotsCent=new int[grid.numDup*grid.numCol*grid.numRow];
			int tot=0;
			count=0;
			numzero=0;
			for (i=0;i<grid.numRow;i++) {
				for (j=0;j<grid.numCol;j++) {
					for (k=0;k<grid.numDup;k++) {
						area1=0; circ1=0; medInt1=0;
						roi=grid.getBoundingOvalForScoring(j,i,k);
						inds=ImageMeasurer.findSpotsInOval(rt,roi);
						numSpots[count]=inds.length;
						tot=tot+inds.length;
						numNearCent=0;
						for (m=0;m<inds.length;m++) {
							//System.out.println(""+(i+1)+" "+(j+1)+" "+rt.getValue("Area",inds[m])+" "+rt.getValue("Circ.",inds[m]));
							if (rt.getValue("Area",inds[m])>area1) {
								if (ImageMeasurer.spotIsNearCenter(rt,inds[m],roi)) {
									area1=rt.getValue("Area",inds[m]);
									circ1=rt.getValue("Circ.",inds[m]);
									medInt1=rt.getValue("Median", inds[m]);
									numNearCent=numNearCent+1;
								} else {
									//System.out.println("Spot not near center");
								}
							}
						}
						if (area1==0) numzero=numzero+1;
						area[i][j][k]=area1;
						circ[i][j][k]=((double)Math.round(circ1*1000))/1000;
						medInt[i][j][k]=medInt1;
						numSpotsCent[count]=numNearCent;
						count=count+1;
					}
				}
			}
			Arrays.sort(numSpots);
			int ind1=Math.round(numSpots.length/2);
			int ind2=Math.round(numSpots.length*3/4);
			typicalNumSpots=((double)partialSum(numSpots,ind1,ind2))/((double)(ind2-ind1+1));

			Arrays.sort(numSpotsCent);
			ind1=Math.round(numSpotsCent.length/2);
			ind2=Math.round(numSpotsCent.length*3/4);
			typicalNumSpotsNearCent=((double)partialSum(numSpotsCent,ind1,ind2))/((double)(ind2-ind1+1));
			//topRowFrac=((double)partialSum(numSpots,1,grid.numCol))/((double)grid.numCol);
			//System.out.println(topRowFrac);
			System.out.println("typ num1 ="+typicalNumSpots+" typ num2 ="+typicalNumSpotsNearCent);
	}
	
	public void exportResults(String filename) {
		BufferedWriter bw;
		String line;
		File outFile;
		int i,j,k;
		outFile=new File(filename);
		try {
			bw = new BufferedWriter(new FileWriter(outFile));
			line="(1) row (2) column";
			for (k=0;k<numDup;k++) {
				line=line+" (3) colony size";
			//+(k+1);
			}
			for (k=0;k<numDup;k++) {
				line=line+" (4) circularity";//+(k+1);
			}
//			for (k=0;k<numDup;k++) {
//				line=line+"  MedInt-"+(k+1);
//			}
			SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			
			writeln(bw,"# Image analyzed by HT colony grid analyzer "+Constants.IA_VERSION+" on " + fmt.format(new Date()));
			writeln(bw,"# "+line);
			for (i=0;i<numRow;i++) {
				for (j=0;j<numCol;j++) {
					line=(i+1)+"\t"+(j+1);
					for (k=0;k<numDup;k++) {
						line=line+"\t"+Math.round(area[i][j][k]);
					}
					for (k=0;k<numDup;k++) {
						line=line+ "\t" +circ[i][j][k];
					}				
//					for (k=0;k<numDup;k++) {
//						line=line+ " " +Math.round(medInt[i][j][k]);
//					}				
					writeln(bw,line);
				}
			}
			bw.close();
		}
		catch (FileNotFoundException fnf) {
			System.err.println("Could not find the file");
		}
		catch (IOException io) {
			System.err.println("Error reading the file");
		}		
	}
	
	public boolean inconsistentReplicates() {
		boolean result=false;
		if (numDup>1) {
			double corr=computeReplicateCorrelation();
			//System.out.println("corr="+corr);
			if (corr<0.6) {
				System.out.println("Colony replicates look inconsistent - reporting potential error");
				result=true;
			}
		}
		return result;
	}
	
	protected double computeReplicateCorrelation() {
		double[] x=new double[numRow*numCol];
		double[] y=new double[numRow*numCol];
		double xbar, ybar, s, xMag, yMag;
		int i,j,k;
		k=0;
		for (i=0;i<numRow;i++) {
			for (j=0;j<numCol;j++) {
				x[k]=area[i][j][0];
				y[k]=area[i][j][1];
				k=k+1;
			}
		}
		xbar=computeMean(x);
		ybar=computeMean(y);
		s=0;
		xMag=0;
		yMag=0;
		for (i=0;i<x.length;i++) {
			x[i]=x[i]-xbar;
			xMag=xMag+x[i]*x[i];
			y[i]=y[i]-ybar;
			yMag=yMag+y[i]*y[i];
			s=s + x[i]*y[i];
		}
		return (s/(Math.sqrt(xMag)*Math.sqrt(yMag)));
	}
	
	protected static double computeMean(double[] x) {
		double s;
		int i;
		s=0;
		for (i=0; i<x.length; i++) {
			s=s+x[i];
		}
		return (s/(double)x.length);
	}
	
	protected double computeMedian(double[] list) {
		Arrays.sort(list);
		int ind1=list.length/2;	//this value will automatically be rounded down
		double med;
		if (list.length==2*ind1) {
			med = (list[ind1]+list[ind1-1])/2;
		} else {
			med = list[ind1];
		}
		return med;
	}
	
	public boolean isBetterThan(MeasuredResults oldResults) {
		boolean retVal=false;
		if (numzero<oldResults.numzero) retVal=true;
		if ((oldResults.numzero<(0.33*numCol*numRow*numDup))) {  //an attempt was made to improve the old results for a different reason
			//think about adding something here
		}
		return retVal;
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
	private int partialSum(int[] list, int start, int stop) {
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
}

