/*
 * Created on Mar 20, 2006
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
 */
import java.io.IOException;

import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.measure.ResultsTablePlus;

public class ColonyGrid96 extends ColonyGrid {
	static int SEARCH_WIDTH = 130;

	static final double PLATE_EDGE_HEIGHT_FRAC=0.055;
	static final double PLATE_EDGE_WIDTH_FRAC=0.043;
	static final int MINIMUM_PLATE_HEIGHT=1150;;
	static final int MINIMUM_PLATE_WIDTH=1650;;
	static final int NUM_ROW=8;
	static final int NUM_COL=12;
	static final int NUM_DUP=1;

	public ColonyGrid96(ImagePlus im, int x1, int y1, int x2, int y2) { //for use in manual mode
		numRow=NUM_ROW;
		numCol=NUM_COL;
		numDup=NUM_DUP;
		heightTol=300;

		setUpGrid(im,x1,y1,x2,y2);
	}
	public ColonyGrid96(ImagePlus im, ResultsTablePlus rt) throws IOException {
		numRow=NUM_ROW;
		numCol=NUM_COL;
		numDup=NUM_DUP;
		heightTol=300;

		setUpGrid(im, rt);
	}
	
	public ColonyGrid96(ImagePlus im, ResultsTablePlus rt, ColonyGrid old_grid, int rep) {
		numRow=NUM_ROW;
		numCol=NUM_COL;
		numDup=NUM_DUP;
		heightTol=300;

		super.setUpGrid(im, rt, old_grid, rep);
	}
	public void setUpGrid(ImagePlus im, int x1, int y1, int x2, int y2) {  //This version is used for the manual processing
		slope=((double)y2-(double)y1)/((double)x2-(double)x1);
		gridWidth=((double)x2-(double)x1);
		fullSpace=gridWidth/11;
		gridHeight=fullSpace*7;
		leftBound=(double)x1+slope*gridHeight/2.0;
		gridTop=(double)y1+slope*gridWidth/2.0;
		halfSpace=fullSpace/2.0;
		gridBoxW=halfSpace*1.8;  //*1.6;
		gridBoxD=gridBoxW/2;
	}
	protected void setGridParameters(ImagePlus im, ResultsTablePlus rt, int w, int h) {
		gridWidth=(rightBound-leftBound);
		fullSpace=gridWidth/11;
		halfSpace=fullSpace/2;	//not needed for this grid
		gridHeight=fullSpace*7;
		gridTop=(h-gridHeight)/2;	//Initial guess
		gridBoxW=halfSpace*1.6;
		gridBoxD=gridBoxW/2;
	}

	public OvalRoi getBoundingOval(int i, int j, int k) {
		//returns an oval that should enclose the colony at the grid position (i,j) (colony replicate k)
		int top;
		int left;
		int width=(int)Math.round(gridBoxW);
		int height=(int)Math.round(gridBoxW);
		// There is no second colony in this grid
			top=(int)Math.round(gridTop+j*fullSpace - (5.5-i)/11*gridWidth*slope - gridBoxD);
			left=(int)Math.round(leftBound-gridBoxD + i*fullSpace + (3.5-j)/7*gridHeight*slope);
		OvalRoi roi=new OvalRoi(left,top,width,height);
		
		return roi;
	}
	

	protected int getMax(int[] list) {
		// Returns the index at which the list achieves it's maximum value
		// Overrides inherited function
		int retInd,ind=0;
		int val=list[0];
		double close;
		int i;
		for (i=0;i<list.length;i++) {
			if (list[i]>val) {
				val=list[i];
				ind=i;
			}
		}
		i=1; retInd=0;
		close=val*0.8;	//requires that you get at least 80% of the true max
		boolean notGoneTooFar=true;
		boolean goneFarEnough=false;
		while ((i<=ind) & (notGoneTooFar)) {
			retInd=i;
			if (list[i]>close) {
				goneFarEnough=true;
			}
			if ((goneFarEnough)&(list[i]<list[i-1])) {
				notGoneTooFar=false;
				retInd=i-1;
			}
			i=i+1;
		}
		return retInd;
	}
}
