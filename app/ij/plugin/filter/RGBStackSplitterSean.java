/*
 * Created on Mar 6, 2006
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

import ij.*;
import ij.measure.Calibration;

public class RGBStackSplitterSean extends RGBStackSplitter {
	
	/** Splits the specified RGB image or stack into three 8-bit grayscale images or stacks. */
	public void split(ImagePlus imp) {
		split(imp.getStack(), true);
		String title = imp.getTitle();
		Calibration cal = imp.getCalibration();
		if (!IJ.altKeyDown())
			imp.hide();
		ImagePlus rImp = new ImagePlus(title+" (red)",red);
		rImp.setCalibration(cal);
		if (IJ.isMacOSX()) IJ.wait(500);
		ImagePlus gImp = new ImagePlus(title+" (green)",green);
		gImp.setCalibration(cal);
		if (IJ.isMacOSX()) IJ.wait(500);
		ImagePlus bImp = new ImagePlus(title+" (blue)",blue);
		bImp.setCalibration(cal);
	}

}
