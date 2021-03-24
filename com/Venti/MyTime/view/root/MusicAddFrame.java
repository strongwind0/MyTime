package com.Venti.MyTime.view.root;

import com.Venti.MyTime.service.RootService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicAddFrame extends JInternalFrame implements ActionListener {
	private JTextField txtMusicPath;
	private JTextField txtName;
	private JButton btnChoose;
	private JButton btnSave;
	private JButton btnCencle;

	/**
	 * Create the frame.
	 */
	public MusicAddFrame() {
		super("添加音乐",false,true,false,true);
		setBounds(100, 100, 600, 450);
		getContentPane().setLayout(null);
		
		JLabel lblMusic = new JLabel("音乐路径");
		lblMusic.setFont(new Font("微软雅黑", Font.BOLD, 25));
		lblMusic.setBounds(15, 80, 100, 50);
		getContentPane().add(lblMusic);
		
		txtMusicPath = new JTextField();
		txtMusicPath.setBounds(125, 80, 300, 50);
		getContentPane().add(txtMusicPath);
		txtMusicPath.setColumns(10);
		
		btnChoose = new JButton("选择音乐");
		btnChoose.addActionListener(this);
		btnChoose.setBounds(450, 90, 100, 30);
		getContentPane().add(btnChoose);
		
		JLabel lblRename = new JLabel("重命名 ");
		lblRename.setFont(new Font("微软雅黑", Font.BOLD, 25));
		lblRename.setBounds(15, 175, 100, 50);
		getContentPane().add(lblRename);
		
		txtName = new JTextField();
		txtName.setBounds(125, 175, 300, 50);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		btnSave = new JButton("保存");
		btnSave.setFont(new Font("微软雅黑", Font.BOLD, 30));
		btnSave.addActionListener(this);
		btnSave.setBounds(100, 300, 100, 50);
		getContentPane().add(btnSave);
		
		btnCencle = new JButton("取消");
		btnCencle.setFont(new Font("宋体", Font.BOLD, 30));
		btnCencle.addActionListener(this);
		btnCencle.setBounds(300, 300, 100, 50);
		getContentPane().add(btnCencle);

	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnChoose){
			txtMusicPath.setText(RootService.chooserMusic(this));
		}else if(e.getSource()==btnSave){
			RootService.saveMusic(txtMusicPath.getText(),txtName.getText(),this);
		}
	}
}
