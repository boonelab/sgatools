/*
 * Created on Mar 7, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package colonyMeasure;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Sean
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import ij.ImagePlus;
import ij.gui.NewImage;
import ij.gui.OvalRoi;
import ij.gui.Roi;
import ij.gui.ShapeRoi;
import ij.measure.ResultsTablePlus;
import ij.process.ByteBlitter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class ColonyGrid {
	
	//This is a superclass which should never actually be directly instantiated
	
	static int SEARCH_WIDTH = 100;
	
	public static final int GRID_96=0;
	public static final int GRID_384=1;
	public static final int GRID_768a=2;
	public static final int GRID_768b=3;
	public static final int GRID_1536=4;
	
	public static final int PREPROC_STAND=0;
	public static final int PREPROC_MB=1;
	
	public static final int AUTO_CROP_DECIDE_OPTION=10;
	public static final int AUTO_CROP_OPTION=11;
	public static final int PRECROP_OPTION=12;
	
	public static final double PLATE_EDGE_START_FRAC=.02;
	
	double leftBound,rightBound,gridTop,gridWidth;
	double fullSpace, halfSpace, gridBoxW, gridBoxD, slope, gridHeight;
	int numCol,numRow;		//number of columns and rows in the grid
	int numDup;				//number of duplicates of each colony.
	int heightTol;
	ImagePlus im11;

	public ImagePlus maskedImage;
	
	public ColonyGrid() {
	}
		
	public ColonyGrid(ImagePlus im, ResultsTablePlus rt) {
	}
	
	public ColonyGrid(ImagePlus im, ResultsTablePlus rt, ColonyGrid old_grid) {
	}
	
	protected void setUpGrid(ImagePlus im, ResultsTablePlus rt) throws IOException {
		System.out.println("1");
		int i,j,left,right,tempNum;
		int w=im.getWidth();
		int h=im.getHeight();
		int numCent[] = new int[SEARCH_WIDTH];
		double sdval[] = new double[SEARCH_WIDTH];
		double temparr[];
		int centInds[];
		int startVal;
		double top = h*0.05;
		double bot = h*0.95;
		double x,y;
		im11=new ImagePlus("title",im.getProcessor());
		System.out.println("2");
		//int numF=rt.getCounter();	//Should be the first time the counter is accessed
		// ********* Finding the left bound of the grid
		int maxNum=0;
		startVal=(int)Math.round(((double)w)*PLATE_EDGE_START_FRAC);
		int search_width=SEARCH_WIDTH+startVal;
		int loops = 0;
		while (maxNum<4) {
			numCent=getCentNumsFromLeft(rt, numCent, top, bot,search_width,startVal+10);
			sdval=getSDvals(im.getProcessor(), sdval, top, bot,search_width,startVal+10,1);
			for (i=0;i<numCent.length;i++) {
				if (sdval[i]<(computeMean(sdval)*1.1)) numCent[i]=0;
			}
			maxNum=getMaxNum(numCent);
			search_width=search_width+50;
			//System.out.println("search width="+search_width+" maxNum="+maxNum);
			
			//Timeout for invalid files
			if(loops==100){ throw new IOException("Timeout on image! - invalid image?"); };
			loops++;

		}
		System.out.println("loops="+loops);
		System.out.println("maxNum="+maxNum);
		int maxCentInd=getMax(numCent);
		centInds=getCenterInds(maxCentInd,top,bot,numCent[maxCentInd],rt);
		double xVals[] = new double[numCent[maxCentInd]];
		for (i=0; i<numCent[maxCentInd]; i++) {
			xVals[i]=rt.getValue("X",centInds[i]);
		}
		leftBound=computeMean(xVals);
		
		// ***** Finding the slant of the grid
		slope=getImageSlant(centInds,numCent[maxCentInd],rt);
		
		// ***** Finding the right bound of the grid
		maxNum=0;
		startVal=(int)Math.round(2*((double)im.getWidth())*PLATE_EDGE_START_FRAC);
		search_width=SEARCH_WIDTH+startVal;
		while (maxNum<4) {
			numCent=getCentNumsFromRight(rt, w, numCent, top, bot,search_width,startVal);
			sdval=getSDvals(im.getProcessor(), sdval, top, bot,search_width,startVal,-1);
			for (i=0;i<numCent.length;i++) {
				if (sdval[i]<(computeMean(sdval)*1.1)) numCent[i]=0;
			}
			maxNum=getMaxNum(numCent);
			maxCentInd=getMax(numCent);
			search_width=search_width+50;
		}
		maxCentInd=getMax(numCent);
		centInds=getCenterInds(w-maxCentInd-11,top,bot,numCent[maxCentInd],rt);
		xVals = new double[numCent[maxCentInd]];
		for (i=0; i<numCent[maxCentInd]; i++) {
			xVals[i]=rt.getValue("X",centInds[i]);
		}
		rightBound=computeMean(xVals);
		System.out.println("left="+leftBound);
		System.out.println("right="+rightBound);
				
		//**** Filling out the rest of the grid info
		setGridParameters(im, rt, w, h);
		gridTop=adjustGridTop(rt,w);
		adjustGridSlant(rt,im);
	}
	
	protected void setUpGrid(ImagePlus im, ResultsTablePlus rt, ColonyGrid old_grid, int rep) { //For taking a second shot at an image
		int i,j,left,right,tempNum;
		int w=im.getWidth();
		int h=im.getHeight();
		int numCent[] = new int[SEARCH_WIDTH];
		double sdval[] = new double[SEARCH_WIDTH];
		int centInds[];
		int startVal;
		double top = h*0.05;
		double bot = h*0.95;
		double x,y;
		im11=new ImagePlus("title",im.getProcessor());
		
		//int numF=rt.getCounter();	//Should be the first time the counter is accessed
		// ********* Finding the left bound of the grid
		int maxNum=0;
		startVal=(int)Math.round(((double)im.getWidth())*PLATE_EDGE_START_FRAC);
		int search_width=SEARCH_WIDTH+startVal;
		while (maxNum<4) {
			numCent=getCentNumsFromLeft(rt, numCent, top, bot,search_width,startVal+10);
			sdval=getSDvals(im.getProcessor(), sdval, top, bot,search_width,startVal+10,1);
			for (i=0;i<numCent.length;i++) {
				if (sdval[i]<(computeMean(sdval)*1.1)) numCent[i]=0;
			}
			maxNum=getMaxNum(numCent);
			search_width=search_width+50;
		}
		int maxCentInd=getMax(numCent);
		centInds=getCenterInds(maxCentInd,top,bot,numCent[maxCentInd],rt);
		double xVals[] = new double[numCent[maxCentInd]];
		for (i=0; i<numCent[maxCentInd]; i++) {
			xVals[i]=rt.getValue("X",centInds[i]);
		}
		leftBound=computeMean(xVals);	// This should be the same left bound as before
		halfSpace=old_grid.halfSpace*((old_grid.numCol)/(old_grid.numCol-(rep+1)*0.5));
		fullSpace=halfSpace*2;
		if (rep>0) { //widen the grid to the left as well
			if (old_grid.numDup>1) {
				leftBound=leftBound-halfSpace;
			} else {
				leftBound=leftBound-fullSpace;
			}
		}
		
		// ***** Finding the slant of the grid
		slope=getImageSlant(centInds,numCent[maxCentInd],rt);
		
		// ***** Finding the right bound of the grid
			// Guessing that the following should have been the right bound
			if (old_grid.numDup>1) {
			 rightBound=old_grid.rightBound+halfSpace;
			} else {
			 rightBound=old_grid.rightBound+fullSpace;
			}
		//System.out.println("leftBound="+leftBound+" rightBound="+rightBound);
				
		//**** Filling out the rest of the grid info
		setGridParameters(im, rt, w, h);
		gridTop=adjustGridTop(rt,w);
		adjustGridSlant(rt,im);
		checkForGridImprovementBySmallTranslation(); //Look to see if the grid is the right size, but just misplaced. I figured that this is only worth trying on this second measurement attempt.
		adjustGridSlant(rt,im);
		gridTop=adjustGridTop(rt,w);
	}
	
	public void setUpGrid(ImagePlus im, int x1, int y1, int x2, int y2) {  //This version is used for the manual processing
		System.out.println("Should not get here (setUpGrid in ColonyGrid.java)");
		System.out.println("This method should be overridden");
	}
	
	protected void autoAdjustParameters() { //This function is used in manual processing mode to allow a more precise fit of the grid position to the colony locations
		//Use linear regression to find the height, width, and position of the grid
	}
	
	protected void setGridParameters(ImagePlus im, ResultsTablePlus rt, int w, int h) {
		/** Should be overridden by subclass **/
		System.out.println("Should not get here setGridParam");
		setGridParameters(im, rt, w, h);
	}
		
	protected void adjustGridSlant(ResultsTablePlus rt, ImagePlus im) {
		int i;
		double slopes[]=new double[numRow];
		int xpos=numCol/2; // xpos is only used to find the center of the row
		for (i=0;i<numRow;i++) {
			slopes[i]=computeRowSlope(rt,xpos,i);
		}
		slope = computeMedian(slopes); //adjusts the value for the grid's slant
	}

	private double computeRowSlope(ResultsTablePlus rt, int xpos, int ypos) {
		int MAX_ARR_LEN=200;
		int top=(int)Math.round(gridTop+ypos*fullSpace - ((numCol-1)/2-xpos)/(numCol-1)*gridWidth*slope - gridBoxD + 5);
		int bot=top+(int)Math.round(gridBoxW);
		Roi roi=new Roi((int)Math.round(leftBound)-10,top,(int)Math.round(gridWidth)+20,(int)Math.round(gridBoxW)-10);
		int inds[]=new int[MAX_ARR_LEN];
		int i;
		//ImagePlus im2=new ImagePlus("temp",im.getProcessor());
		//im2.setRoi(roi);
		//im2.show();
		double x,y,x1[],y1[],x2[],y2[];
		x1=new double[MAX_ARR_LEN]; y1=new double[MAX_ARR_LEN];
		int num=0;
		int numIterations=0;
		while ((num<5)&(numIterations<20)) {
			num=0;
			for (i=0;i<rt.numRows;i++) {
				x=rt.getValue("X",i);
				y=rt.getValue("Y",i);
				if (roi.contains((int)Math.round(x),(int)Math.round(y))) {
					inds[num]=i;
					x1[num]=x;
					y1[num]=y;
					num=num+1;
				}
			}
			top = (int)Math.round(top + gridBoxD);
			bot = (int)Math.round(bot + gridBoxD);
			numIterations++;
		}
		x2=new double[num];
		y2=new double[num];
		for (i=0;i<num;i++){
			x2[i]=x1[i];
			y2[i]=y1[i];
		}
		double retval=0;
		if (num>4) {
			retval=computeSlope(x2,y2);
		}
		return retval;
	}
	
	public OvalRoi getBoundingOval(int i, int j, int k) {
		/** This function should be overridden by specific functions in each of the grid subclasses **/
		System.out.println("Shouldn't get here");
		//returns an oval that should enclose the colony at the grid position (i,j) (colony replicate k)
		int top;
		int left;
		int width=(int)Math.round(gridBoxW);
		int height=(int)Math.round(gridBoxW);
		if (k==0) {  //First Colony
			top=(int)Math.round(gridTop+j*fullSpace - (11.5-i)/23*gridWidth*slope - gridBoxD);
			left=(int)Math.round(leftBound-gridBoxD + i*fullSpace + (7.5-j)/15*gridHeight*slope);
		} else {  //Second Colony
			top=(int)Math.round(gridTop+j*fullSpace - (11.5-i)/23*gridWidth*slope - gridBoxD + halfSpace);
			left=(int)Math.round(leftBound-gridBoxD + i*fullSpace + (7.5-j)/15*gridHeight*slope + halfSpace);
		}
		OvalRoi roi=new OvalRoi(left,top,width,height);
		
		return roi;
	}
	
	public OvalRoi getBoundingOvalForScoring(int i, int j, int k) {
		// This function is present to allow grids with duplicates (e.g. the 768 grid)
		// to use a different function for scoring purposes. In that case, the function
		// will be overridden by the specific grid subclass
		return getBoundingOval(i,j,k);
	}
	
	public void applyGridMask(ImagePlus im) {
		maskedImage=applyGridMask(im,0);
	}
	
	private ImagePlus applyGridMask(ImagePlus im, int flag) {  //this flag is not currently used except to distinguish the two methods
		//colNum should be either 1 or 2 to specify the first or second colonies
		int k,temp,i,j;
		int w=im.getWidth();
		int top, left;
		int width, height;
		OvalRoi roi;
		Rectangle r;
		ImageProcessor ip3,ip4;
		i=0;
		
		im11=im;  //This image should be stored as the unmasked image
		
		Rectangle r2=new Rectangle(0,0,w,im.getHeight());
		ImagePlus im2=NewImage.createByteImage(im.getTitle()+"_masked",r2.width,r2.height,1,NewImage.FILL_BLACK);
		ImagePlus im3=NewImage.createByteImage("temp",r2.width,r2.height,1,NewImage.FILL_BLACK);
		ip3=im3.getProcessor();
		ImageProcessor ip1=im.getProcessor();
		byte[] p1=(byte[])ip1.getPixels();
		ImageProcessor ip2=im2.getProcessor();
		byte[] p2=(byte[]) ip2.getPixels();
		ByteBlitter bb1=new ByteBlitter((ByteProcessor)ip2);
		ByteBlitter bb2=new ByteBlitter((ByteProcessor)ip3);
		// The new image is blank, we'll just reinsert the pixels inside of our grid
		for (k=0;k<p1.length;k++) {
			p2[k]=(p1[k]);
		}
		width=(int)Math.round(gridBoxW);
		height=(int)Math.round(gridBoxW);
		for (j=0;j<numRow;j++) {
			for (i=0;i<numCol;i++) {
				for (k=0;k<numDup;k++) {
					roi=getBoundingOval(i,j,k);
					r=roi.getBounds();
					ip1.setRoi(roi);
					ImagePlus tempIm=new ImagePlus("tempIm",ip1);
					ip4=ip1.getMask();
					bb2.copyBits(ip4,r.x,r.y,ByteBlitter.MAX);
				}
			}
		}
		
		byte[] p3=(byte[]) ip3.getPixels();
		for (k=0;k<p1.length;k++) {
			if (p3[k]!=0) {
				p3[k]=(byte)1;
			}
		}
		bb1.copyBits(ip3,0,0,ByteBlitter.MULTIPLY);
		for (k=0;k<p1.length;k++) {
			//p2[k]=(byte)(255-p2[k]);
		}
		//im2.show();
		return im2;
	}
		
	protected double adjustGridTop(ResultsTablePlus rt, int w) {
		double medH=getMiddleRowHeight();//gridTop + 8*fullSpace;
		double boxT=medH-gridBoxD*0.75;	//the grid should be a little closer to the top than the bottom of the image
		double boxB=medH+gridBoxD*0.75;
		double gridTop0=gridTop;
		double boxL=w*0.05;
		double boxR=w*0.95;
		int[] cents=new int[50];
		int tempNum=0;
		int i,j,k,l;
		OvalRoi roi;
		int[] inds;
		double x,y;
		for (i=0;i<rt.numRows;i++) {
			x=rt.getValue("X",i);
			y=rt.getValue("Y",i);
			if (isInCorrectRow(x,y,boxT,boxB)) {
				cents[tempNum]=i;
				tempNum=tempNum+1;
			}
		}
		// Looking for the middle row
		double boxT0=boxT;
		double boxB0=boxB;
		int dir=1;
		int stepNum=1;
		int stepSize=2;
		while ((tempNum<3) & (stepNum<10)) {	//probably looked too high
			boxT=boxT0+stepSize*stepNum*dir;
			boxB=boxB0+stepSize*stepNum*dir;
			gridTop=gridTop+stepSize*stepNum*dir;
			cents=new int[50];
			tempNum=0;
			for (i=0;i<rt.numRows;i++) {
				x=rt.getValue("X",i);
				y=rt.getValue("Y",i);
				if (isInCorrectRow(x,y,boxT,boxB)) {//((x>boxL)&(x<boxR)&(y>boxT)&(y<boxB)) {
					cents[tempNum]=i;
					tempNum=tempNum+1;
				}
			}
			if (dir==-1) {
				stepNum=stepNum+1;
			}
			dir=dir*-1;
		}
		double yCoords[]=new double[tempNum];
		for (i=0;i<tempNum;i++) {
			yCoords[i]=rt.getValue("Y",cents[i]);
		}
		double newPos=gridTop0;
		if (yCoords.length>0) {
			newPos = computeMedian(yCoords);
			newPos = newPos - (numRow/2)*fullSpace;
		} else {
			System.out.println("Could not properly adjust the grid top location");
		}
		
		// ****** Check if we're one row too low ******
		//Measuring current guess - counting for the current bottom row
		boxT=newPos+gridHeight-fullSpace*0.75;
		boxB=boxT+fullSpace;
		boxL=leftBound;
		boxR=rightBound;
		int numGoodCurrent=0;
		for (i=0;i<rt.numRows;i++) {
			x=rt.getValue("X",i);
			y=rt.getValue("Y",i);
			if ((x>boxL)&(x<boxR)&(y>boxT)&(y<boxB)&(rt.getValue("Circ.", i)>0.4)&(rt.getValue("Area",i)>50)) {
				//System.out.println(rt.getValue("Area",i)+" "+rt.getValue("Circ.", i));
				numGoodCurrent=numGoodCurrent+1;
			}
		}
		//Measuring alternate guess - counting for the alternate top row
		boxT=newPos-fullSpace-fullSpace*0.25;
		boxB=boxT+fullSpace;
		int numGoodAlt=0;
		for (i=0;i<rt.numRows;i++) {
			x=rt.getValue("X",i);
			y=rt.getValue("Y",i);
			if ((x>boxL)&(x<boxR)&(y>boxT)&(y<boxB)&(rt.getValue("Circ.", i)>0.4)&(rt.getValue("Area",i)>50)) {
				numGoodAlt=numGoodAlt+1;
			}
		}
		if ((numGoodAlt>numGoodCurrent) & ((newPos-fullSpace)>0)) {
			newPos=newPos-fullSpace;
			System.out.println("Adjusting grid top position - raising");
		}
		//*********************************************************
		
		// ****** Check if we're one row too high ******
		//Measuring current guess - counting for the current top row
		boxT=newPos-fullSpace*0.5;	//I'm not sure why these numbers are 0.75 and 0.25 above ??
		boxB=boxT+fullSpace;
		boxL=leftBound;
		boxR=rightBound;
		numGoodCurrent=0;
		for (i=0;i<rt.numRows;i++) {
			x=rt.getValue("X",i);
			y=rt.getValue("Y",i);
			if ((x>boxL)&(x<boxR)&(y>boxT)&(y<boxB)&(rt.getValue("Circ.", i)>0.4)&(rt.getValue("Area",i)>50)) {
				//System.out.println(rt.getValue("Area",i)+" "+rt.getValue("Circ.", i));
				numGoodCurrent=numGoodCurrent+1;
			}
		}
		//Measuring alternate guess - counting for the alternate bottom row
		boxT=newPos+gridHeight+fullSpace-fullSpace*0.5;
		boxB=boxT+fullSpace;
		numGoodAlt=0;
		for (i=0;i<rt.numRows;i++) {
			x=rt.getValue("X",i);
			y=rt.getValue("Y",i);
			if ((x>boxL)&(x<boxR)&(y>boxT)&(y<boxB)&(rt.getValue("Circ.", i)>0.4)&(rt.getValue("Area",i)>50)) {
				numGoodAlt=numGoodAlt+1;
			}
		}
		if ((numGoodAlt>numGoodCurrent) & ((newPos-fullSpace)>0)) {
			newPos=newPos+fullSpace;
			System.out.println("Adjusting grid top position - lowering "+numGoodCurrent+" "+numGoodAlt);
		}
		//*********************************************************
		
		
		return newPos;
	}
		
	protected void checkForGridImprovementBySmallTranslation() {
		//Check grid position by translating the grid up or down one row and left/right one column
		int numFilled[][]=new int[3][3];
		int i,j,k,l,m,p,num,num2,maxNum;
		double gridTop0=gridTop;
		double gridLeft0=leftBound;
		int h=im11.getHeight();
		int w=im11.getWidth();
		double circ;
		OvalRoi roi;
		int[] inds;
		int thresh = ImageMeasurer.getColonyThreshold(im11);
		for (i=-1;i<2;i++) {
			for (m=-1;m<2;m++) {
				gridTop=gridTop0+i*this.halfSpace;
				leftBound=gridLeft0+m*this.halfSpace;
				if ((gridTop>0)&(leftBound>0)&(gridTop+gridHeight<h)&(leftBound+gridWidth<w)) {
					ImagePlus masked=this.applyGridMask(im11,0);
					//masked.show();
					ResultsTablePlus rtF=ImageMeasurer.getParticles(masked,thresh,50,5000);
					numFilled[i+1][m+1]=0;
					for (j=0;j<this.numRow;j++) {
						for (k=0;k<this.numCol;k++) {
							for (l=0;l<this.numDup;l++) {
								roi=this.getBoundingOvalForScoring(k,j,l);
								inds=ImageMeasurer.findSpotsInOval(rtF,roi);
								num=0;
								num2=0;
								for (p=0;p<inds.length;p++) {
									circ=rtF.getValue("Circ.",inds[p]);
									if ((ImageMeasurer.spotIsNearCenter(rtF,inds[p],roi))&(circ>0.5)) num=num+1;
								}
								if (num>0) numFilled[i+1][m+1]=numFilled[i+1][m+1]+1;
							}
						}
					}
				} else {
				numFilled[i+1][m+1]=0;
				}
				System.out.println(i+" "+m+" num="+numFilled[i+1][m+1]);
			}
		}
		//Default to the center location
		maxNum=numFilled[1][1];
		gridTop=gridTop0;
		leftBound=gridLeft0;
		
		for (i=-1;i<2;i++) {
			for (m=-1;m<2;m++) {
				//System.out.println("Checking "+i+" "+m+" "+numFilled[i+1][m+1]);
				if (numFilled[i+1][m+1]>maxNum) {
					maxNum=numFilled[i+1][m+1];
					gridTop=gridTop0+i*this.halfSpace;
					leftBound=gridLeft0+m*this.halfSpace;
					//System.out.println("new maxNum "+i+" "+m+" max="+maxNum);
				}
			}
		}
	}
	
	protected boolean isInCorrectRow(double x, double y, double boxT, double boxB) {
		boolean retval = false;
		int j;
		OvalRoi roi;
		
		int x0=(int)Math.round(x);
		int y0=(int)Math.round(y);
		
		if ((y>boxT)&(y<boxB)) {	//Don't bother with the ovalRoi unless y is in the right range
			roi=getBoundingOval(Math.round(numRow/2),0,0);
			Rectangle r=roi.getBoundingRect();
			for (j=0; j<numCol; j++) {
				roi=getBoundingOval(j,Math.round(numRow/2),0);
				if (roi.contains(x0,y0)) {
					retval=true;
				}
			}
		}
		return retval;
	}
	
	protected int[] getCentNumsFromLeft(ResultsTablePlus rt, int[] numCent, double top, double bot, int search_width, int minVal) {
		int i;
		int j;
		int left;
		int right;
		int tempNum;
		double x,y,circ;
		numCent=new int[search_width];
		for (i=0;i<minVal;i++) {
			numCent[i]=0;
		}
		for (i=minVal; i<search_width; i++) {
			left=i;
			right=i+10;
			tempNum=0;
			for (j=0; j<rt.numRows; j++) {
				x=rt.getValue("X",j);
				y=rt.getValue("Y",j);
				circ=rt.getValue("Circ.",j);
				if ((x>left)&(x<right)&(y>top)&(y<bot)&(circ>0.25)) {
					tempNum=tempNum+1;
				}
			}
			numCent[i]=tempNum;
			//System.out.println("left="+left+" numCent="+tempNum);
		}
		return numCent;
	}
	protected double[] getSDvals(ImageProcessor ip, double[] sdval, double top, double bot, int search_width, int minVal, int dir) {
		int i;
		int j,k;
		int w=ip.getWidth();
		int mid,start,stop,per1,temparr;
		int len,temp;
		double x,vals0[];
		double y;
		len=(int)Math.round(bot-top);
		start=0; stop=len;
		vals0=new double[len];
		sdval=new double[search_width];
		for (i=0; i<sdval.length; i++) {
			for (j=start;j<stop;j++) {
				temp=0;
				for (k=i+4;k<i+7;k++) {
					if (dir>0) {
						temp=temp+ip.getPixel(k,j);
					} else {
						temp=temp+ip.getPixel(w-k-1,j);
					}
				}
				vals0[j-start]=temp;
			}
			sdval[i]=computeSD(vals0);
		}
		return sdval;
	}
	
	protected boolean powerOf2(int num) {
		int num2;
		if (num==1) {
			return true;
		} else {
			num2=num/2;
			if ((double)num2 != ((double)num)/2.0) {
				return false;
			} else return powerOf2(num2);
		}
	}
	
	protected int[] getCentNumsFromRight(ResultsTablePlus rt, int w, int[] numCent, double top, double bot, int search_width, int minVal) {
		int i;
		int j;
		int left;
		int right;
		int tempNum;
		double x,y,circ;
		numCent=new int[search_width];
		for (i=0;i<minVal;i++) {
			numCent[i]=0;
		}
		for (i=minVal; i<search_width; i++) {
			left=w-i-11;
			right=w-i-1;
			tempNum=0;
			for (j=0; j<rt.numRows; j++) {
				x=rt.getValue("X",j);
				y=rt.getValue("Y",j);
				circ=rt.getValue("Circ.",j);
				if ((x>left)&(x<right)&(y>top)&(y<bot)&(circ>0.25)) {
					tempNum=tempNum+1;
				}
			}
			numCent[i]=tempNum;
		}
		return numCent;
	}

	protected double getMiddleRowHeight() {
		double top=gridTop+(Math.round(numRow/2))*fullSpace;
		return top;	
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
	
	protected double findTypicalVerticalStep(double[] yVals) {
		if (yVals.length>0) {
			double[] steps=new double[yVals.length-1];
			int i;
			Arrays.sort(yVals);
			for (i=0; i<steps.length; i++) {
				steps[i]=yVals[i+1]-yVals[i];
			}
			return computeMedian(steps);
		} else return 0;
	}
	
	protected double getImageSlant(int[] centInds, int numC, ResultsTablePlus rt) {
		double x[]=new double[numC];
		double y[]=new double[numC];
		int i;
		for (i=0;i<numC;i++) {
			x[i]=rt.getValue("X",centInds[i]);
			y[i]=rt.getValue("Y",centInds[i]);
		}
		double res = -1*computeSlope(y, x);
		return res;  //this is the slope determined by the points
	}

	protected double computeSlope(double[] x, double[] y) {
		int i;
		int numC=x.length;
		double xbar=computeMean(x);
		double ybar=computeMean(y);
		double SSxx=0;
		double SSxy=0;
		double SSyy=0;
		for (i=0;i<numC;i++) {
			SSxx=SSxx+(x[i]-xbar)*(x[i]-xbar);
			SSxy=SSxy+(x[i]-xbar)*(y[i]-ybar);
			SSyy=SSyy+(y[i]-ybar)*(y[i]-ybar);
		}
		double res=SSxy/SSxx;
		return res;
	}
	
	protected double computeMean(double[] x) {
		double sum=0;
		int i;
		for (i=0;i<x.length;i++) {
			sum=sum+x[i];
		}
		return sum/x.length;
	}
	protected double computeSD(double[] x) {
		double mean=computeMean(x);
		int i;
		double sum=0;
		for (i=0;i<x.length;i++) {
			sum=sum+(x[i]-mean)*(x[i]-mean);
		}
		sum=sum/(x.length-1);
		return Math.sqrt(sum);
	}
	
	protected int[] getCenterInds(int pos, double top, double bot, int numCent, ResultsTablePlus rt) {
		int[] list=new int[numCent];
		int i,j,left=pos;
		int right=pos+10;
		double x,y,circ;
		j=0;
		for (i=0;i<rt.numRows;i++){
			x=rt.getValue("X",i);
			y=rt.getValue("Y",i);
			circ=rt.getValue("Circ.", i);
			if ((x>left)&(x<right)&(y>top)&(y<bot)&(circ>0.25)) {
				list[j]=i;
				j=j+1;
			}
		}
		return list;
	}
	protected boolean checkIfGridExceedsBoundsOfImage() {
		int i,j,k;
		OvalRoi roi;
		Rectangle r;
		boolean result=false;
		
		//Check 4 corners of grid
		result=(result | checkIfGridSpotExceedsBoundsOfImage(0, 0, 0));
		result=(result | checkIfGridSpotExceedsBoundsOfImage(numCol-1, 0, numDup-1));
		result=(result | checkIfGridSpotExceedsBoundsOfImage(0, numRow-1, 0));
		result=(result | checkIfGridSpotExceedsBoundsOfImage(numCol-1, numRow-1, numDup-1));
				
		return result;
	}

	private boolean checkIfGridSpotExceedsBoundsOfImage(int i, int j, int k) {
		OvalRoi roi;
		Rectangle r;
		roi=getBoundingOval(i,j,k);
		r=roi.getBounds();
		if (k>0) {
			return ((r.x<0)|((r.x+r.width)>maskedImage.getWidth())|(r.y<0)|((r.y+r.height)>(maskedImage.getHeight())/2));
		} else {
			return ((r.x<0)|((r.x+r.width)>maskedImage.getWidth())|(r.y<0)|((r.y+r.height)>maskedImage.getHeight()));
		}
	}
	
	protected void adjustGridPositionForExpandedImage(int numExtraPixels) {
		gridTop=gridTop+numExtraPixels;
		leftBound=leftBound+numExtraPixels;
	}
	protected int getMaxNum(int[] list) {
		//returns the maximum value of the list
		int i;
		int res;
		res=-1; //default
		for (i=0;i<list.length;i++) {
			if (list[i]>res) {
				res=list[i];
			}
		}
		return res;
	}
	protected double getMaxNum(double[] list) {
		//returns the maximum value of the list
		int i;
		double res;
		res=-1; //default
		for (i=0;i<list.length;i++) {
			if (list[i]>res) {
				res=list[i];
			}
		}
		return res;
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
	protected int getMax(double[] list) {
		// Returns the index at which the list achieves it's maximum value
		// Overrides inherited function
		int retInd,ind=0;
		double val=list[0];
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
