package colonyMeasure;

import ij.ImagePlus;
import ij.gui.ImageCanvas;
import ij.gui.Line;
import ij.gui.OvalRoi;
import ij.gui.PointRoi;
import ij.gui.Roi;
import ij.gui.ShapeRoi;
import ij.measure.Calibration;
import ij.measure.ResultsTablePlus;
import ij.plugin.filter.ImageTools;
import ij.plugin.filter.RGBStackSplitterSean;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.MouseInputListener;

public class ManualProcessor implements MouseInputListener {
	
	int x1,x2,y1,y2,numPts,thresh;
	double mag;
	ImagePlus im,im0;
	ImageCanvas canvas;
	Calibration calibration;
	ManualProcToolbar mpt;
	ColMeasureProgram cmp;
	ColonyGrid grid;
	boolean gridAppliedFlag,autoCroppedFlag;
	String filename;
	Line userLine;
	ShapeRoi gridRoi;
	
	public ManualProcessor(ImageProcessor ip, String fn, ColMeasureProgram cmp1) {
		im0=new ImagePlus("Original Image",ip);	//Stored so we can go back to it if we need to
		im=im0;
		cmp=cmp1;
		filename=fn;
		
		// Setting default values:
		gridAppliedFlag=false;
		autoCroppedFlag=false;
		x1=0; x2=0; y1=0; y2=0;
		
		if (im.getBitDepth()==24) {
			ip=getRedChannel(ip);
		} else {
			ip=ip.convertToByte(false);
		}
		im=new ImagePlus("Red channel",ip);
		im.killRoi();
		im.show();
		numPts=0;
		canvas=im.getWindow().getCanvas();
		mag=canvas.getMagnification();
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		mpt=new ManualProcToolbar(this);
	}
	private ImageProcessor getRedChannel(ImageProcessor ip) {
		RGBStackSplitterSean splitter=new RGBStackSplitterSean();
		splitter.split(new ImagePlus("tempImage",ip));
		ImagePlus red=new ImagePlus("tempImage",splitter.red);
		return red.getProcessor();
	}
	protected void doEstimatedCropping() {
		int deltaX=Math.abs(x2-x1);
		int deltaY=Math.abs(y2-y1);
		int cropL=x1 - deltaY*2/3 - (int)Math.round((double)deltaX*0.06);
		if (cropL<1) cropL=1;
		int cropR=x2 + deltaY*2/3 + (int)Math.round((double)deltaX*0.06);
		if (cropR>im.getWidth()) cropR=im.getWidth()-1;
		int cropTop=y1 - deltaY - (int)Math.round((double)deltaX*0.05);
		if (cropTop<1) cropTop=1;
		int cropBot=y1 + deltaY + (int)Math.round((double)deltaX*0.8);
		if (cropBot>im.getHeight()) cropBot=im.getHeight()-1;
		im.setRoi(cropL,cropTop,cropR-cropL,cropBot-cropTop);
		ImagePlus temp=new ImagePlus("Cropped",im.getProcessor().crop());
		im.close();
		im=temp;
		
		//adjust x1, x2, y1, and y2 based on cropping
		x1=x1-cropL;
		x2=x2-cropL;
		y1=y1-cropTop;
		y2=y2-cropTop;
	}
	protected void autoCrop() {
		int h=im.getHeight();
		int w=im.getWidth();

		double baseline=ImageMeasurer.getBaselinePixelValue(im);
		int cropBot = ImageMeasurer.findVertCropPos(im, h-1, -1, baseline);
		int cropTop = ImageMeasurer.findVertCropPos(im, 0, 1, baseline);
		int cropL = ImageMeasurer.findHorCropPos(im,0,1, baseline);
		int cropR = ImageMeasurer.findHorCropPos(im,w-1,-1, baseline);
		im.setRoi(cropL,cropTop,cropR-cropL,cropBot-cropTop);
		ImagePlus temp=new ImagePlus("Cropped",im.getProcessor().crop());
		im.close();
		im=temp;
		im.show();
		updateMouseListener();
		mpt.toFront();
	}
	private void updateMouseListener() {
		canvas=im.getWindow().getCanvas();
		mag=canvas.getMagnification();
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}
	protected void applyGrid() {
		switch (cmp.gridType) {
			case ColonyGrid.GRID_96: grid=new ColonyGrid96(im,x1,y1,x2,y2); break;
			case ColonyGrid.GRID_384: grid=new ColonyGrid384(im,x1,y1,x2,y2); break;
			case ColonyGrid.GRID_768a: grid=new ColonyGrid768a(im,x1,y1,x2,y2); break;
			case ColonyGrid.GRID_1536: grid=new ColonyGrid1536(im,x1,y1,x2,y2); break;
		}
		grid.applyGridMask(im);
		im.close();
		im=grid.maskedImage;
		im.show();
		mpt.toFront();
	}
	protected void autoAdjustGrid() {
		switch (cmp.gridType) {
			case ColonyGrid.GRID_96: grid=new ColonyGrid96(im,x1,y1,x2,y2); break;
			case ColonyGrid.GRID_384: grid=new ColonyGrid384(im,x1,y1,x2,y2); break;
			case ColonyGrid.GRID_768a: grid=new ColonyGrid768a(im,x1,y1,x2,y2); break;
			case ColonyGrid.GRID_1536: grid=new ColonyGrid1536(im,x1,y1,x2,y2); break;
		}
		grid.autoAdjustParameters();
		grid.applyGridMask(im);
		im.close();
		im=grid.maskedImage;
		im.show();
		mpt.toFront();
	}
	protected void rotateAndRecalculate() {
		//Rotates the image to make the user's drawn line level, and then recalculates new values for x1, x2, y1, and y2
		double x0,y0; //the center of the old image, around which the image will be rotated
		double xC,yC; //the center of the new image, after rotation and cropping
		double slope=((double)y2-(double)y1)/((double)x2-(double)x1);
		ImageProcessor ip;
		int h=im.getHeight();
		int w=im.getWidth();
		int cropTop=(int)Math.round(h*0.025);
		int cropH=(int)Math.round(h*0.95);
		int cropL=(int)Math.round(w*0.025);
		int cropW=(int)Math.round(w*0.95);
		double angle=Math.atan(slope)/Math.PI*-180;
		
		ImagePlus temp=im;	//to allow for disposing of the old image later
		ip=im.getProcessor();
		ip.rotate(angle);
		ip.setRoi(cropL, cropTop, cropW, cropH);
		ip=ip.crop();
		im=new ImagePlus("Rotated",ip);
		temp.close();
		im.show();
		updateMouseListener();
		
		//Recalculate x1,y1,x2, and y2
		x0=(double)w/2.0;
		y0=(double)h/2.0;
		xC=x0-cropL;
		yC=y0-cropTop;
		angle=angle/180*Math.PI;
		double d1=ImageMeasurer.distance((double)x1, (double)y1, x0, y0);
		double angle0=Math.atan2((double)y1 - y0,(double)x1 - x0);
		angle0=angle0;
		x1=(int)Math.round(xC+d1*Math.cos(angle0+angle));
		y1=(int)Math.round(yC+d1*Math.sin(angle0+angle));
		double d2=ImageMeasurer.distance((double)x2, (double)y2, x0, y0);
		angle0=Math.atan2((double)y2 - y0, (double)x2 - x0);
		angle0=angle0;
		x2=(int)Math.round(xC+d2*Math.cos(angle0+angle));
		y2=(int)Math.round(yC+d2*Math.sin(angle0+angle));
		slope=0;
	}
	
	protected OvalRoi getGridOval(int i, int j, int k, double dist, double angle,int numCol) {
		int centX,centY;
		double fracX,fracY;
		fracX=((double)j + (double)k/2.0) / ((double)(numCol-1));
		fracY=((double)i + (double)k/2.0) / ((double)(numCol-1));
		centX=(int)Math.round((double)x1 + fracX*dist*Math.cos(angle) - fracY*dist*Math.sin(angle));
		centY=(int)Math.round((double)y1 + fracX*dist*Math.sin(angle) + fracY*dist*Math.cos(angle));
		return new OvalRoi(centX-11,centY-11,21,21);
	}
	
	protected ShapeRoi getGridRoi(int x1, int y1, int x2, int y2, int gridType) {
		int i,j,k;
		int numRow=0,numCol=0,numDup=0;
		double dist=ImageMeasurer.distance(x1, y1, x2, y2);
		double angle=Math.atan(((double)y2-(double)y1)/((double)x2-(double)x1));
		switch (gridType) {
			case ColonyGrid.GRID_96: {
				numRow=ColonyGrid96.NUM_ROW;
				numCol=ColonyGrid96.NUM_COL;
				numDup=ColonyGrid96.NUM_DUP;
				break;}
			case ColonyGrid.GRID_384: {
				numRow=ColonyGrid384.NUM_ROW;
				numCol=ColonyGrid384.NUM_COL;
				numDup=ColonyGrid384.NUM_DUP;
				break;}
			case ColonyGrid.GRID_768a: {
				numRow=ColonyGrid768a.NUM_ROW;
				numCol=ColonyGrid768a.NUM_COL;
				numDup=ColonyGrid768a.NUM_DUP;
				break;}
			case ColonyGrid.GRID_1536: {
				numRow=ColonyGrid1536.NUM_ROW;
				numCol=ColonyGrid1536.NUM_COL;
				numDup=ColonyGrid1536.NUM_DUP;
				break;}
		}
		OvalRoi upperLeft=getGridOval(0,0,0,dist,angle,numCol);
		ShapeRoi res = new ShapeRoi(upperLeft);
		for (i=0;i<3;i++) {
			for (j=0;j<3;j++) {
				for (k=0;k<numDup;k++) {
					int a=Math.round(i*(numRow-1)/2);
					int b=Math.round(j*(numCol-1)/2);
					res.or(new ShapeRoi(getGridOval(a,b,k,dist,angle,numCol)));
				}
			}
		}
		return res;
	}

	public void measureAndExport() {
		thresh=setThreshold(im,x1,x2,y1,y2);
		ResultsTablePlus rtF=ImageMeasurer.getParticles(grid.maskedImage,thresh,10,5000);
		MeasuredResults imageResults=new MeasuredResults(grid,rtF);
		imageResults.exportResults(filename);
	}
	
	private int setThreshold(ImagePlus im, int x1, int x2, int y1, int y2) {
		int h=y2-y1;
		int w=x2-x1;
		im.setRoi(x1+w/6,y1+h/6,(2*w)/3,(2*h)/3);
		ImagePlus im2=ImageTools.copy(im,im.getRoi());
		ImageProcessor ip=im2.getProcessor();
		return ((ByteProcessor)ip).getAutoThreshold();
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	/**	if (numPts==0) {
			numPts=1;
			x1=e.getX();
			y1=e.getY();
			im.setRoi(new PointRoi(x1,y1,im));
			im.updateAndDraw();
			System.out.println("x="+x1+" y="+y1);
		}
		if (numPts==1) {
			numPts=0;
			x2=e.getX();
			y2=e.getY();
			im.setRoi(new Line(x1,y1,x2,y2));
			im.updateAndDraw();
			System.out.println("x2="+x2+" y2="+y2);
		}
	**/
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x1=(int)Math.round(e.getX()/mag);
		y1=(int)Math.round(e.getY()/mag);
		//userLine=new Line(x1,y1,im);
		System.out.println("Pressed: x="+x1+" y="+y1);
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		x2=(int)Math.round(e.getX()/mag);
		y2=(int)Math.round(e.getY()/mag);
		System.out.println("Released : x="+x2+" y="+y2);
		if (x1>x2) { //Swap the points so that (x1,y1) will be to the left of (x2,y2)
			int tempX=x1;
			int tempY=y1;
			x1=x2; y1=y2;
			x2=tempX; y2=tempY;
		}
		gridRoi=getGridRoi(x1,y1,x2,y2,cmp.gridType);
		im.setRoi(gridRoi);
		mpt.updateStatusText("Proceed to generate grid and measure");
		mpt.toFront();
	}
	public void mouseDragged(MouseEvent e) {
		x2=(int)Math.round(e.getX()/mag);
		y2=(int)Math.round(e.getY()/mag);
		userLine=new Line(x1,y1,x2,y2);
		im.setRoi(userLine);
	}
	public void mouseMoved(MouseEvent e) {
	}

}
