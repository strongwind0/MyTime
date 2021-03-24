package com.Venti.MyTime.view.root;

import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.service.RootService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAddFrame extends JInternalFrame implements ActionListener {
	private JTextField txtUsername;
	private JTextField txtAccount;
	private JPasswordField pwdPassword;
	private JLabel lblAvatar;
	private JButton btnSave;
	private JButton btnCancel;

	private static final SimpleDateFormat format=new SimpleDateFormat("yyMMddHHmmssSSS");
	private String defaultPath= "/image/userAvatar.jpg";

	/**
	 * Create the frame.
	 */
	public UserAddFrame() {
		super("添加用户",true,true,false,true);
		setBounds(100, 100, 750, 500);
		getContentPane().setLayout(null);

		JLabel lblUsername = new JLabel("用户名");
		lblUsername.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblUsername.setBounds(300, 80, 100, 50);
		getContentPane().add(lblUsername);

		JLabel lblAccount = new JLabel("账号");
		lblAccount.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblAccount.setForeground(new Color(0, 0, 0));
		lblAccount.setBounds(300, 170, 100, 50);
		getContentPane().add(lblAccount);

		JLabel label = new JLabel("密码");
		label.setFont(new Font("微软雅黑", Font.BOLD, 30));
		label.setBounds(300, 260, 100, 50);
		getContentPane().add(label);

		lblAvatar = new JLabel("");
		lblAvatar.setIcon(new ImageIcon(UserAddFrame.class.getResource(defaultPath)));
		lblAvatar.setBounds(30, 50, 225, 300);
		getContentPane().add(lblAvatar);

		btnSave = new JButton("确认");
		btnSave.addActionListener(this);
		btnSave.setFont(new Font("微软雅黑", Font.BOLD, 30));
		btnSave.setBounds(300, 360, 100, 50);
		getContentPane().add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(500, 360, 100, 50);
		getContentPane().add(btnCancel);

		txtUsername = new JTextField();
		txtUsername.setFont(new Font("楷体", Font.BOLD, 30));
		txtUsername.setBounds(400, 80, 300, 50);
		getContentPane().add(txtUsername);
		txtUsername.setColumns(10);

		txtAccount = new JTextField();
		txtAccount.setBounds(400, 170, 300, 50);
		getContentPane().add(txtAccount);
		txtAccount.setColumns(10);

		pwdPassword = new JPasswordField();
		pwdPassword.setFont(new Font("Times New Roman", Font.BOLD, 30));
		pwdPassword.setBounds(400, 260, 300, 50);
		getContentPane().add(pwdPassword);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSave) {
			User user = new User(txtUsername.getText(),
					txtAccount.getText(),
					pwdPassword.getText(),
					defaultPath,
					format.format(new Date()));
			int result = RootService.saveUser(this,user);
			if(result==1){
				txtAccount.setText("");
				txtUsername.setText("");
				pwdPassword.setText("");
			}else if(result == -1) {
				txtAccount.setText("");
			}
		}else if(e.getSource()==btnCancel) {
			this.dispose();
		}
	}
}
