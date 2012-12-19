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

import java.awt.Rectangle;

import ij.ImagePlus;
import ij.gui.NewImage;
import ij.gui.Roi;
import ij.process.ImageProcessor;

public class ImageTools {
	
	public static ImagePlus copy(ImagePlus im, Roi r) {
		int i,j,k1,k2,offset1,offset2;
		int w=im.getWidth();
		Rectangle r2=r.getBounds();
		ImagePlus im2=NewImage.createByteImage(im.getTitle()+"_copy",r2.width,r2.height,1,NewImage.FILL_BLACK);
		ImageProcessor ip1=im.getProcessor();
		byte[] p1=(byte[])ip1.getPixels();
		ImageProcessor ip2=im2.getProcessor();
		byte[] p2=(byte[]) ip2.getPixels();
		for (i=r2.y;i<r2.y+r2.height;i++) {
			offset1 = i*w;
			offset2 = (i-r2.y)*im2.getWidth();
			for (j=r2.x;j<r2.x+r2.width;j++) {
				k1=offset1+j;
				k2=offset2+(j-r2.x);
				p2[k2] = (byte)(p1[k1]);
			}
		}
		return im2;
	}
	
	public static ImagePlus copy(ImagePlus im, int x, int y, int w, int h) {
		Roi r=new Roi(x,y,w,h);
		return copy(im,r);
	}
	
	public static ImagePlus threshold(ImagePlus im, int thresh) {
		int k,temp;
		int w=im.getWidth();
		Rectangle r2=new Rectangle(0,0,w,im.getHeight());
		ImagePlus im2=NewImage.createByteImage(im.getTitle()+"_thresh",r2.width,r2.height,1,NewImage.FILL_BLACK);
		ImageProcessor ip1=im.getProcessor();
		byte[] p1=(byte[])ip1.getPixels();
		ImageProcessor ip2=im2.getProcessor();
		byte[] p2=(byte[]) ip2.getPixels();
		for (k=0;k<p1.length;k++) {
			temp=(int)p1[k];
			if (temp<0) {
				temp=temp+255;
			}
			if (temp>thresh) {
				p2[k]=(byte)0;
			} else {
				p2[k]=(byte)255;
			}
		}
		return im2;
	}

}
