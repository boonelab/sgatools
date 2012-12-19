/*
 * Created on Apr 3, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package colonyMeasure;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Sean
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ColMeasureAboutWindow extends JFrame {
	JPanel cp;
	JTextArea ta;
	
	public ColMeasureAboutWindow() {
		// Displays an "About this program" window
		setTitle("About the Colony Measuring Program");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cp = new JPanel(new GridLayout());
		setContentPane(cp);
		ta=new JTextArea("Written by Sean Collins (2006) under Gnu General Public License.\n\nThis program analyzes .jpg images. It expects an approximate resolution of 160 dots per cm. Images should either be cropped to the size of the experimental plate or have a black background outside of the plate.\n\nVersion 1.1.3");
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		cp.add(ta);
		setVisible(true);
		pack();
		setSize(400,200);
		validate();
	}

}
