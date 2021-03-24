package com.Venti.MyTime.view.user;

import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserViewFrame extends JInternalFrame implements ActionListener {
	private JTextField txtName;
	private JLabel lblAvatar;
	private JLabel lblMyAccount;
	private JButton btnChoose;
	private JButton btnSave;
	private JButton btnCancel;

	private User user;
	private String imageFilePath;

	/**
	 * Create the frame.
	 */
	public UserViewFrame(User user) {
		super("个人信息",true,true,false,true);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(null);

		lblAvatar = new JLabel("");
		lblAvatar.setBounds(30, 30, 225, 300);
		lblAvatar.setIcon(new ImageIcon(user.getPath()));
		getContentPane().add(lblAvatar);
		
		JLabel lblName = new JLabel("昵称");
		lblName.setFont(new Font("新宋体", Font.BOLD, 30));
		lblName.setBounds(300, 60, 100, 50);
		getContentPane().add(lblName);
		
		txtName = new JTextField(user.getUsername());
		txtName.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		txtName.setBounds(420, 60, 300, 50);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAccount = new JLabel("账号");
		lblAccount.setFont(new Font("新宋体", Font.BOLD, 30));
		lblAccount.setBounds(300, 160, 100, 50);
		getContentPane().add(lblAccount);
		
		lblMyAccount = new JLabel(user.getUserAccountNumber());
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

		this.user=user;
	}

	
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource()==btnSave) {
				String name = txtName.getText();
				lblAvatar.setIcon(new ImageIcon(UserService.saveUser(user,
						name,
						imageFilePath,
						this)));
				System.out.println(user.getPath());
			}else if(e.getSource()==btnChoose) {
				imageFilePath = UserService.chooserAvatar(this);
				if(!imageFilePath.equals(""))
					lblAvatar.setIcon(new ImageIcon(imageFilePath));
			}else if(e.getSource()==btnCancel) {
				this.dispose();
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
