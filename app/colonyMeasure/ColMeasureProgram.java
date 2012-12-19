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
 */

import ij.ImagePlus;
import ij.process.*;
import ij.gui.ImageCanvas;
import ij.gui.Line;
import ij.gui.PointRoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
//import javax.swing.filechooser.FileFilter;

public class ColMeasureProgram /*extends JFrame implements ActionListener*/ {

	JMenuItem itemMeasure, itemMeasureFolder, itemManualMeasure, itemQuit;
	JMenuItem itemShowAbout;
	JCheckBoxMenuItem itemGrid96, itemGrid384, itemGrid768a, itemGrid768b, itemGrid1536;
	JCheckBoxMenuItem itemAutoCropDecide, itemAutoCrop, itemPreCrop;
	JMenuBar mb;
	JPanel cp;
	JFileChooser fc;
	JLabel statusLabel;
	int numProcessed;
	int numErrorsFound;
	int gridType;
	int cropType;
	String errorFileNames[];
	int errorCodes[];
	
	static int ERR_OTHER=1;
	static int ERR_TOO_FEW_COL=2;
	static int ERR_TOO_MANY_COL=3;
	static int ERR_CROP_ERROR=4;
	static int ERR_REPLICATES=5;
	
	static String[] ERR_MESSAGE={"","Processing required multiple attempts - may be incorrect:","Grid Alignment - Too few colonies:","Grid Alignment - Too many colonies:","Possible cropping error:","Replicate colony measurements are inconsistent:"}; 
	
	/*	
	public static void main(String[] args) {
		ColMeasureProgram cmp = new ColMeasureProgram();
	}
	
	public ColMeasureProgram() {
		numErrorsFound=0;
		gridType=ColonyGrid.GRID_768a;	// Default value
		cropType=ColonyGrid.AUTO_CROP_OPTION; //Default value
		setTitle("Colony Measuring Program");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		cp = new JPanel(new BorderLayout());
		setContentPane(cp);
		cp.setSize(400, 400);
		mb = new JMenuBar();
		mb.setBackground(Color.cyan);
		buildFileMenu();
		buildGridTypeMenu();
		buildCropTypeMenu();
		buildAboutMenu();
		statusLabel=new JLabel("Ready");
		cp.add(statusLabel,BorderLayout.NORTH);
		initFileChooser();
		setJMenuBar(mb);
		mb.validate();
		setVisible(true);
		pack();
		setSize(400,400);
		validate();		
	}
	private void buildGridTypeMenu() {
		JMenu gridMen;
		
		gridMen = new JMenu("Grid Type");
		gridMen.setBackground(Color.cyan);
		itemGrid96 = new JCheckBoxMenuItem("96 colonies",false);
		itemGrid96.addActionListener(this);
		gridMen.add(itemGrid96);
		itemGrid384 = new JCheckBoxMenuItem("384 colonies",false);
		itemGrid384.addActionListener(this);
		gridMen.add(itemGrid384);
		itemGrid768a = new JCheckBoxMenuItem("768 colonies - diagonal replicates",true);
		itemGrid768a.addActionListener(this);
		gridMen.add(itemGrid768a);
		itemGrid1536 = new JCheckBoxMenuItem("1536 colonies",false);
		itemGrid1536.addActionListener(this);
		gridMen.add(itemGrid1536);
		//itemGrid768b = new JCheckBoxMenuItem("768 colonies - alternate packing",false);
		//itemGrid768b.addActionListener(this);
		//gridMen.add(itemGrid768b);
		mb.add(gridMen);
	}
	private void buildCropTypeMenu() {
		JMenu cropMen;
		
		cropMen = new JMenu("Image Crop Option");
		cropMen.setBackground(Color.cyan);
		itemAutoCropDecide = new JCheckBoxMenuItem("Automatically Choose Method",false);
		itemAutoCropDecide.addActionListener(this);
		cropMen.add(itemAutoCropDecide);
		itemAutoCrop = new JCheckBoxMenuItem("Always Autodetect Plate Edges",true);
		itemAutoCrop.addActionListener(this);
		cropMen.add(itemAutoCrop);
		itemPreCrop = new JCheckBoxMenuItem("Images Are Already Cropped to Plate Edges",false);
		itemPreCrop.addActionListener(this);
		cropMen.add(itemPreCrop);
		mb.add(cropMen);
	}
	private void buildFileMenu() {
		JMenu fileMen;
		
		fileMen = new JMenu("File    ");
		fileMen.setBackground(Color.cyan);
		itemMeasure = new JMenuItem("Process Single Image");
		itemMeasure.addActionListener(this);
		fileMen.add(itemMeasure);
		itemMeasureFolder = new JMenuItem("Process Folder of Images");
		itemMeasureFolder.addActionListener(this);
		fileMen.add(itemMeasureFolder);
		itemManualMeasure = new JMenuItem("Manually analyze 1 image");
		itemManualMeasure.addActionListener(this);
		fileMen.add(itemManualMeasure);
		itemQuit = new JMenuItem("Exit");
		itemQuit.addActionListener(this);
		fileMen.add(itemQuit);
		mb.add(fileMen);
	}
	private void buildAboutMenu() {
		JMenu aboutMen;
		
		aboutMen = new JMenu("About this program");
		aboutMen.setBackground(Color.cyan);
		itemShowAbout = new JMenuItem("Show info");
		itemShowAbout.addActionListener(this);
		aboutMen.add(itemShowAbout);
		mb.add(aboutMen);
	}
	private void initFileChooser() {
		fc = new JFileChooser();
		//String sepchar = File.separator;
		//String rootDirName = "C:"+File.separator;
		//File rootDir = new File(rootDirName);
		//fc.setCurrentDirectory(rootDir);
	}
	private void updateLabel(JLabel label, String str) {
		label.setText(str);
		update(this.getGraphics());
	}
	private void processFile(String path, int showOption) throws IOException {
		String temp[];
		int temp2[];
		int i;
		if ((path.endsWith(".jpg"))|(path.endsWith(".JPG"))) {
			numProcessed=numProcessed+1;
			ImagePlus im=new ImagePlus(path);
			ImageProcessor ip=im.getProcessor();
			String filename=path+".dat";
			ImageMeasurerOptions imo=new ImageMeasurerOptions(gridType,cropType,showOption);
			ImageMeasurer imM=new ImageMeasurer(ip,filename,imo);
			if (showOption==ImageMeasurer.SHOW_NOTHING) {
				updateLabel(statusLabel,"Processed "+numProcessed+" files");
			}
			if (imM.ERROR_FLAG>0) {
				temp=new String[numErrorsFound+1];
				temp2=new int[numErrorsFound+1];
				for (i=0;i<numErrorsFound;i++) {
					temp[i]=errorFileNames[i];
					temp2[i]=errorCodes[i];
				}
				temp[numErrorsFound]=path;
				temp2[numErrorsFound]=imM.ERROR_FLAG;
				numErrorsFound++;
				errorFileNames=temp;
				errorCodes=temp2;
			}
		}
	}
	private void processFileManual(String path, int showOption) {
		if ((path.endsWith(".jpg"))|(path.endsWith(".JPG"))) {
			numProcessed=numProcessed+1;
			ImagePlus im=new ImagePlus(path);
			ImageProcessor ip=im.getProcessor();
			String filename=path+".dat";
			//ImageMeasurerOptions imo=new ImageMeasurerOptions(gridType,cropType,showOption);
			ManualProcessor mp=new ManualProcessor(ip,filename,this);
		}
	}
	public void exportErrorList(File dir) {
		BufferedWriter bw;
		File outFile;
		int i,j;
		String filename="List of potentially incorrect files.txt";
		//outFile=new File(dir.getPath()+File.separator+filename);
		outFile=new File(dir,filename);
		try {
			bw = new BufferedWriter(new FileWriter(outFile));
			writeln(bw,"Files for which the processing may not have worked correctly");
			for (i=5;i>0;i--) { //iterate over the error types
				writeln(bw,"");
				writeln(bw,ERR_MESSAGE[i]);
				for (j=0;j<errorFileNames.length;j++) {
					if (errorCodes[j]==i) {
						writeln(bw,errorFileNames[j]);
					}
				}
			}
			bw.close();
		}
		catch (FileNotFoundException fnf) {
			System.err.println("Could not make the error file");
		}
		catch (IOException io) {
			System.err.println("Error reading the file");
		}		
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
	private void processFolder(File dir) {
		File[] children = dir.listFiles();
		if (children == null) {
			processFile(dir.getPath(), 0);
		} else {
			//updateLabel(statusLabel,dir.getPath());
			for (int i=0; i<children.length; i++) {
				// Get filename of file or directory
				//String subfilename = dir.getPath() + File.separator + children[i];
				//updateLabel(statusLabel,subfilename);
				processFolder(children[i]);
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		File selectedFile;
		
		if (e.getSource() == itemMeasure) {
			fc.setFileSelectionMode(fc.FILES_ONLY);
			int returnval = fc.showOpenDialog(this);
			if (returnval == JFileChooser.APPROVE_OPTION) {
				selectedFile = fc.getSelectedFile();
				String path=selectedFile.getPath();
				numErrorsFound=0;
				processFile(path,ImageMeasurer.SHOW_GRID_OVERLAY);
				updateLabel(statusLabel,"Done processing image");
				if (numErrorsFound>0) {
					updateLabel(statusLabel,"There may have been an error processing this file.");
					File dir=selectedFile.getParentFile();
					exportErrorList(dir);
				}
			}
		}
		if (e.getSource() == itemManualMeasure) {
			fc.setFileSelectionMode(fc.FILES_ONLY);
			int returnval = fc.showOpenDialog(this);
			if (returnval == JFileChooser.APPROVE_OPTION) {
				selectedFile = fc.getSelectedFile();
				String path=selectedFile.getPath();
				numErrorsFound=0;
				processFileManual(path,ImageMeasurer.SHOW_GRID_OVERLAY);
				updateLabel(statusLabel,"Done processing image");
				if (numErrorsFound>0) {
					updateLabel(statusLabel,"There may have been an error processing this file.");
					File dir=selectedFile.getParentFile();
					exportErrorList(dir);
				}
			}
		}
		if (e.getSource() == itemMeasureFolder) {
			fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);
			int returnval = fc.showOpenDialog(this);
			if (returnval == JFileChooser.APPROVE_OPTION) {
				File dir = fc.getSelectedFile();
				numProcessed=0;
				numErrorsFound=0;
				processFolder(dir);
				updateLabel(statusLabel,"Done processing folder");
				if (numErrorsFound>0) {
					updateLabel(statusLabel,"There may have been an error processing at least one of the files. A list of potentially incorrect files has been saved in the folder you selected.");
					exportErrorList(dir);
				}
			}
		}
		if (e.getSource() == itemQuit) {
			this.dispose();
		}
		if (e.getSource() == itemGrid96) {
			gridType=ColonyGrid.GRID_96;
			itemGrid96.setState(true);
			itemGrid384.setState(false);
			itemGrid768a.setState(false);
			itemGrid1536.setState(false);
			//itemGrid768b.setState(false);
		}
		if (e.getSource() == itemGrid384) {
			gridType=ColonyGrid.GRID_384;
			itemGrid96.setState(false);
			itemGrid384.setState(true);
			itemGrid768a.setState(false);
			itemGrid1536.setState(false);
			//itemGrid768b.setState(false);
		}
		if (e.getSource() == itemGrid768a) {
			gridType=ColonyGrid.GRID_768a;
			itemGrid96.setState(false);
			itemGrid384.setState(false);
			itemGrid768a.setState(true);
			itemGrid1536.setState(false);
			//itemGrid768b.setState(false);
		}
		if (e.getSource() == itemGrid1536) {
			gridType=ColonyGrid.GRID_1536;
			itemGrid96.setState(false);
			itemGrid384.setState(false);
			itemGrid768a.setState(false);
			itemGrid1536.setState(true);
			//itemGrid768b.setState(false);
		}
		//if (e.getSource() == itemGrid768b) {
		//	gridType=ColonyGrid.GRID_768b;
		//	itemGrid96.setState(false);
		//	itemGrid384.setState(false);
		//	itemGrid768a.setState(false);
		//	itemGrid768b.setState(true);
		//}
		if (e.getSource() == itemAutoCropDecide) {
			cropType=ColonyGrid.AUTO_CROP_DECIDE_OPTION;
			itemAutoCropDecide.setState(true);
			itemAutoCrop.setState(false);
			itemPreCrop.setState(false);
		}
		if (e.getSource() == itemAutoCrop) {
			cropType=ColonyGrid.AUTO_CROP_OPTION;
			itemAutoCropDecide.setState(false);
			itemAutoCrop.setState(true);
			itemPreCrop.setState(false);
		}
		if (e.getSource() == itemPreCrop) {
			cropType=ColonyGrid.PRECROP_OPTION;
			itemAutoCropDecide.setState(false);
			itemAutoCrop.setState(false);
			itemPreCrop.setState(true);
		}
		if (e.getSource() == itemShowAbout) {
			ColMeasureAboutWindow cma=new ColMeasureAboutWindow();
		}
	}
*/
}
