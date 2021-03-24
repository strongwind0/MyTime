package com.Venti.MyTime.view;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JInternalFrame {

	/**
	 * Create the frame.
	 */
	public AboutFrame() {
		super("版本",false,true,false,false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblDeveloper = new JLabel("开发人员：Venti");
		lblDeveloper.setFont(new Font("楷体", Font.BOLD, 25));
		lblDeveloper.setBounds(15, 15, 250, 50);
		getContentPane().add(lblDeveloper);
		
		JLabel lblNewLabel = new JLabel("版本：2.6.0");
		lblNewLabel.setFont(new Font("楷体", Font.BOLD, 25));
		lblNewLabel.setBounds(70, 100, 250, 50);
		getContentPane().add(lblNewLabel);
		
		JLabel lblTime = new JLabel("开发时间：2020.1.6 23:59:59");
		lblTime.setFont(new Font("楷体", Font.BOLD, 25));
		lblTime.setBounds(15, 200, 385, 41);
		getContentPane().add(lblTime);

	}
}
