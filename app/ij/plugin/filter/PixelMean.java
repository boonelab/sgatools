/*
 * Created on Mar 7, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ij.plugin.filter;

/**
 * @author Sean
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import ij.process.*;
import ij.gui.Roi;
import ij.ImagePlus;

public class PixelMean {
	
	public static double get(ImagePlus im, Roi r1) {
		im.setRoi(r1.getBounds());
		ImageStatistics imStat=im.getStatistics();
		return imStat.mean;
	}
	public static double get(ImagePlus im, int x, int y, int w, int h) {
		Roi r1=new Roi(x,y,w,h);
		return PixelMean.get(im,r1);
	}

}
