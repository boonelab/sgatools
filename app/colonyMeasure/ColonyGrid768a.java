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

import java.awt.Rectangle;
import java.io.IOException;

import ij.ImagePlus;
import ij.gui.NewImage;
import ij.gui.OvalRoi;
import ij.measure.ResultsTablePlus;
import ij.process.ByteBlitter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class ColonyGrid768a extends ColonyGrid {
	
	static int SEARCH_WIDTH = 60;

	static final double PLATE_EDGE_HEIGHT_FRAC=0.030;
	static final double PLATE_EDGE_WIDTH_FRAC=0.03;
	static final int MINIMUM_PLATE_HEIGHT=1150;//1200;
	static final int MINIMUM_PLATE_WIDTH=1650;//1700;
	static final int NUM_ROW=16;
	static final int NUM_COL=24;
	static final int NUM_DUP=2;
	int imageHeight=0;

	public ColonyGrid768a() {
	}
	
	public ColonyGrid768a(ImagePlus im, ResultsTablePlus rt) throws IOException {
		numRow=NUM_ROW;
		numCol=NUM_COL;
		numDup=NUM_DUP;
		heightTol=200;
	
		super.setUpGrid(im, rt);
	}

	public ColonyGrid768a(ImagePlus im, ResultsTablePlus rt, ColonyGrid old_grid, int rep) { //For taking a second shot at an image
		numRow=NUM_ROW;
		numCol=NUM_COL;
		numDup=NUM_DUP;
		heightTol=200;
	
		super.setUpGrid(im, rt, old_grid, rep);
	}
	
	public ColonyGrid768a(ImagePlus im, int x1, int y1, int x2, int y2) {
		numRow=NUM_ROW;
		numCol=NUM_COL;
		numDup=NUM_DUP;
		heightTol=200;
	
		setUpGrid(im, x1, y1, x2, y2);
		
	}
	public void setUpGrid(ImagePlus im, int x1, int y1, int x2, int y2) {  //This version is used for the manual processing
		slope=((double)y2-(double)y1)/((double)x2-(double)x1);
		gridWidth=((double)x2-(double)x1)*((double)numCol -1.0 + 1.0/(double)numDup)/((double)numCol-1.0);
		fullSpace=gridWidth/23.5;
		gridHeight=fullSpace*15.5;
		leftBound=(double)x1+slope*gridHeight/2.0;
		gridTop=(double)y1+slope*gridWidth/2.0;
		halfSpace=fullSpace/2.0;
		gridBoxW=halfSpace*1.7;//*1.55;
		gridBoxD=gridBoxW/2;
	}
	protected void setGridParameters(ImagePlus im, ResultsTablePlus rt, int w, int h) {
		gridWidth=(rightBound-leftBound);
		fullSpace=gridWidth/23.5;
		halfSpace=fullSpace/2;
		gridHeight=fullSpace*15.5;
		gridTop=(h-gridHeight)/2;	//Initial guess
		gridBoxW=halfSpace*1.55;//*1.3;
		gridBoxD=gridBoxW/2;
	}
	
	public OvalRoi getBoundingOval(int i, int j, int k) {
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
		OvalRoi roi=new OvalRoi(left,top + k*imageHeight,width,height);
		if (left<0) {
			System.out.println("Neg val");
		}
		
		return roi;
	}
	
	public void applyGridMask(ImagePlus im) {
		maskedImage=applyGridMask(im,0);
	}
	
	private ImagePlus applyGridMask(ImagePlus im, int flag) {
		//this method overrides the ColonyGrid method
		//It will double the image vertically to separate the replicates so that a larger 
		//circle can be used to identify the colonies
		int k,temp,i,j;
		int w=im.getWidth();
		int h=im.getHeight();
		int top, left;
		int width, height;
		OvalRoi roi;
		Rectangle r;
		ImageProcessor ip1,ip2,ip3,ip4;
		i=0;
		Rectangle r2=new Rectangle(0,0,w,h);
		
		imageHeight=h;	//Setting this value for the object to remember for scoring purposes
		
		ImagePlus im2=NewImage.createByteImage(im.getTitle()+"_masked",r2.width,2*r2.height,1,NewImage.FILL_BLACK);
		ImagePlus im3=NewImage.createByteImage("temp",r2.width,2*r2.height,1,NewImage.FILL_BLACK);
		ip3=im3.getProcessor();
		ip1=im.getProcessor();
		byte[] p1=(byte[])ip1.getPixels();
		ip2=im2.getProcessor();
		byte[] p2=(byte[]) ip2.getPixels();
		ByteBlitter bb2=new ByteBlitter((ByteProcessor)ip2);
		ByteBlitter bb3=new ByteBlitter((ByteProcessor)ip3);
		// The new image is blank, we'll just reinsert the pixels inside of our grid
		for (k=0;k<p1.length;k++) {
			p2[k]=(p1[k]);
		}
		for (k=0;k<p1.length;k++) {
			p2[k+p1.length]=(p1[k]);
		}
		width=(int)Math.round(gridBoxW);
		height=(int)Math.round(gridBoxW);
		for (j=0;j<numRow;j++) {
			for (i=0;i<numCol;i++) {
				for (k=0;k<numDup;k++) {
					roi=getBoundingOval(i,j,k);
					r=roi.getBounds();
					ip1.setRoi(roi);
					//ImagePlus tempIm=new ImagePlus("tempIm",ip1);
					ip4=ip1.getMask();
					bb3.copyBits(ip4,r.x,r.y + k*h,ByteBlitter.MAX);
				}
			}
		}
		
		byte[] p3=(byte[]) ip3.getPixels();
		for (k=0;k<p3.length;k++) {
			if (p3[k]!=0) {
				p3[k]=(byte)1;
			}
		}
		bb2.copyBits(ip3,0,0,ByteBlitter.MULTIPLY);
		for (k=0;k<p1.length;k++) {
			//p2[k]=(byte)(255-p2[k]);
		}
		//im2.show();
		return im2;
	}
		

}
