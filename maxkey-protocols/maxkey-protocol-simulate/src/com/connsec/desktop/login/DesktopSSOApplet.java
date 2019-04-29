package com.connsec.desktop.login;

import javax.swing.JApplet;


import javax.swing.JProgressBar;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.JLabel;

import sun.misc.BASE64Decoder;

/**
 * @author Crystal.Sea
 *
 */
public class DesktopSSOApplet extends JApplet {
	ExecuteSimuKey executeSimuKey;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8708906696879467549L;

	/**
	 * Create the applet.
	 */
	public DesktopSSOApplet() {
		getContentPane().setLayout(null);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(5);
		progressBar.setBounds(31, 105, 330, 57);
		getContentPane().add(progressBar);
		

		JTextPane textPane = new JTextPane();
		textPane.setBounds(91, 55, 82, 21);
		getContentPane().add(textPane);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(162, 32, 54, 15);
		getContentPane().add(lblNewLabel);

	}

	public void init() {
		// Put your code here
		String jsonParam=null;
		String encoderParam=this.getParameter("encoderParam");
		System.out.println("encoder Param : "+encoderParam);
		try {
			byte[] decodeParam=(new BASE64Decoder()).decodeBuffer(encoderParam);
			jsonParam=new String(decodeParam);
			System.out.println("json Paramen :"+jsonParam);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(jsonParam==null) {
			System.out.println("json Param can not been null .");
			return;
		}
		executeSimuKey=new ExecuteSimuKey(jsonParam);
		
		executeSimuKey.executeAll();

	}
}
