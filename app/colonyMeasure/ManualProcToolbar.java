package colonyMeasure;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ManualProcToolbar extends JFrame implements ActionListener, WindowListener, KeyListener {

	JPanel cp,mainPanel,northPanel;
	JTextArea statusText;
	Button cropButton,makeGridButton,measureButton,restartButton,finishedButton,autoAdjButton;
	ManualProcessor mp;

	public ManualProcToolbar(ManualProcessor mp1) {
		mp=mp1;
		addWindowListener(this);
		addKeyListener(this);
		
		setTitle("Manual Processing Toolbar");
		cp = new JPanel(new BorderLayout());
		mainPanel = new JPanel(new GridLayout(5,1));
		northPanel= new JPanel(new BorderLayout());
		setContentPane(cp);
		//cp.setSize(200, 40);
		northPanel.add(new JLabel("Message:"),BorderLayout.NORTH);
		statusText=new JTextArea("Draw a line in the image through the centers of the colonies in the top row");
		statusText.setLineWrap(true);
		statusText.setEditable(false);
		northPanel.add(statusText,BorderLayout.CENTER);
		cropButton = new Button("Auto-Crop Image");
		cropButton.addActionListener(this);
		mainPanel.add(cropButton);
		makeGridButton = new Button("Generate Grid & Measure Image");
		makeGridButton.addActionListener(this);
		mainPanel.add(makeGridButton);
		//measureButton = new Button("Measure Image");
		//measureButton.addActionListener(this);
		//cp.add(measureButton);
		autoAdjButton = new Button("Auto-Adjust Grid & Measure Image");
		autoAdjButton.addActionListener(this);
		mainPanel.add(autoAdjButton);
		restartButton = new Button("Start over");
		restartButton.addActionListener(this);
		mainPanel.add(restartButton);
		finishedButton = new Button("Finished");
		finishedButton.addActionListener(this);
		mainPanel.add(finishedButton);
		cp.add(northPanel,BorderLayout.NORTH);
		cp.add(mainPanel,BorderLayout.CENTER);
		setVisible(true);
		validate();
		pack();
		setSize(250,200);
		validate();
	}
	
	protected void updateStatusText(String str) {
		statusText.setText(str);
		update(this.getGraphics());
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==cropButton) {
			if (!mp.autoCroppedFlag) {
				mp.autoCrop();
				mp.autoCroppedFlag=true;
			} else {
			}
		}
		if (e.getSource()==makeGridButton) {
			if (mp.x2>0) {
				if (!mp.autoCroppedFlag) mp.doEstimatedCropping();
				mp.rotateAndRecalculate();
				mp.applyGrid();
				mp.gridAppliedFlag=true;
				//measure image and export results
				mp.measureAndExport();
				updateStatusText("Results saved to file");
			} else {
				updateStatusText("You need to draw a line on the image first");
			}
		}
		if (e.getSource()==measureButton) {
		}
		if (e.getSource()==autoAdjButton) {
			if (mp.x2>0) {
				mp.rotateAndRecalculate();
				mp.autoAdjustGrid();
				mp.gridAppliedFlag=true;
				mp.measureAndExport();
				updateStatusText("Results saved to file");
			} else {
				updateStatusText("You need to draw a line on the image first");
			}
		}
		if (e.getSource()==restartButton) {
			mp.im.close();
			/**
			mp.im=mp.im0;
			mp.im.show();
			mp.gridAppliedFlag=false;
			mp.canvas=mp.im.getWindow().getCanvas();
			mp.mag=mp.canvas.getMagnification();
			mp.canvas.addMouseListener(mp);
			this.toFront();
			**/
			ManualProcessor mp2=new ManualProcessor(mp.im0.getProcessor(),mp.filename,mp.cmp);
			this.dispose();
			
		}
		if (e.getSource()==finishedButton) {
			mp.im.close();
			this.dispose();
		}

	}
	public void windowActivated(WindowEvent e) {
	}
	public void windowDeactivated(WindowEvent e) {
	}
	public void windowClosing(WindowEvent e) {
		mp.im.close();
		this.dispose();		
	}
	public void windowIconified(WindowEvent e) {	
	}
	public void windowDeiconified(WindowEvent e) {
	}
	public void windowOpened(WindowEvent e) {
	}
	public void windowClosed(WindowEvent e) {
	}
	public void keyPressed(KeyEvent e) {
		System.out.println("got here");
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
		int k = e.getKeyCode();
		if (k==KeyEvent.VK_LEFT) {
			
		}
		if (k==KeyEvent.VK_RIGHT) {
			
		}
		if (k==KeyEvent.VK_UP) {
			
		}
		if (k==KeyEvent.VK_DOWN) {
			System.out.println("Down");			
		}
		if (k==KeyEvent.VK_PERIOD) {
			System.out.println("Period");
		}
	}

}
