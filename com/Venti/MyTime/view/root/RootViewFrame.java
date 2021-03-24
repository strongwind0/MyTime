package com.Venti.MyTime.view.root;

import com.Venti.MyTime.entity.Root;
import com.Venti.MyTime.service.RootService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RootViewFrame extends JInternalFrame implements ActionListener{

	private JTextField txtName;
	private JLabel lblAvatar;
	private JLabel lblMyAccount;
	private JButton btnChoose;
	private JButton btnSave;
	private JButton btnCancel;
	
	private Root root;
	private String imageFilePath;

	/**
	 * Create the frame.
	 */
	public RootViewFrame(Root root) {
		super("个人信息",true,true,false,true);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);
		
		lblAvatar = new JLabel("");
		lblAvatar.setBounds(30, 30, 225, 300);
		lblAvatar.setIcon(new ImageIcon(root.getPath()));
		getContentPane().add(lblAvatar);
		
		JLabel lblName = new JLabel("昵称");
		lblName.setFont(new Font("新宋体", Font.BOLD, 30));
		lblName.setBounds(300, 60, 100, 50);
		getContentPane().add(lblName);
		
		txtName = new JTextField(root.getRootName());
		txtName.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		txtName.setBounds(420, 60, 300, 50);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAccount = new JLabel("账号");
		lblAccount.setFont(new Font("新宋体", Font.BOLD, 30));
		lblAccount.setBounds(300, 160, 100, 50);
		getContentPane().add(lblAccount);
		
		lblMyAccount = new JLabel(root.getRootAccountNumber());
		lblMyAccount.setFont(new Font("Times New Roman", Font.BOLD, 30));
		lblMyAccount.setBounds(420, 160, 300, 50);
		getContentPane().add(lblMyAccount);
		
		btnChoose = new JButton("选择");
		btnChoose.addActionListener(this);
		btnChoose.setBounds(66, 375, 113, 27);
		getContentPane().add(btnChoose);
		
		btnSave = new JButton("保存");
		btnSave.addActionListener(this);
		btnSave.setForeground(Color.ORANGE);
		btnSave.setFont(new Font("微软雅黑", Font.BOLD, 30));
		btnSave.setBounds(350, 300, 150, 50);
		getContentPane().add(btnSave);
		
		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		btnCancel.setBounds(550, 300, 150, 50);
		getContentPane().add(btnCancel);
		
		this.root = root;

	}

	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource()==btnSave){
				String name = txtName.getText();
				lblAvatar.setIcon(new ImageIcon(RootService.saveRoot(root,
						name,
						imageFilePath,
						this)));
			}else if(e.getSource()==btnCancel){
				this.dispose();
			}else if(e.getSource()==btnChoose){
				imageFilePath = RootService.chooserAvatar(this);
				lblAvatar.setIcon(new ImageIcon(imageFilePath));
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
