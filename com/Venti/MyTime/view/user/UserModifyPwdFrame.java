package com.Venti.MyTime.view.user;

import com.Venti.MyTime.dao.UserDao;
import com.Venti.MyTime.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserModifyPwdFrame extends JInternalFrame implements ActionListener {
	private JPasswordField pwdOldPwd;
	private JPasswordField pwdNewPwd;
	private JPasswordField pwdConfirmPwd;
	private JLabel txtError;
	private JButton btnSave;
	private JButton btnCancel;

	private User user;

	/**
	 * Create the frame.
	 */
	public UserModifyPwdFrame(User user) {
		super("修改密码",false,true,false,false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblOldPwd = new JLabel("旧密码：");
		lblOldPwd.setFont(new Font("微软雅黑", Font.BOLD, 25));
		lblOldPwd.setBounds(30, 30, 125, 30);
		getContentPane().add(lblOldPwd);
		
		JLabel lblNewPwd = new JLabel("新密码：");
		lblNewPwd.setFont(new Font("微软雅黑", Font.BOLD, 25));
		lblNewPwd.setBounds(30, 80, 125, 30);
		getContentPane().add(lblNewPwd);
		
		JLabel lblConfirm = new JLabel("确认密码：");
		lblConfirm.setFont(new Font("微软雅黑", Font.BOLD, 25));
		lblConfirm.setBounds(30, 130, 125, 30);
		getContentPane().add(lblConfirm);
		
		pwdOldPwd = new JPasswordField();
		pwdOldPwd.setFont(new Font("Times New Roman", Font.BOLD, 25));
		pwdOldPwd.setBounds(155, 30, 250, 30);
		getContentPane().add(pwdOldPwd);
		
		pwdNewPwd = new JPasswordField();
		pwdNewPwd.setFont(new Font("Times New Roman", Font.BOLD, 25));
		pwdNewPwd.setBounds(155, 80, 250, 30);
		getContentPane().add(pwdNewPwd);
		
		pwdConfirmPwd = new JPasswordField();
		pwdConfirmPwd.setFont(new Font("Times New Roman", Font.BOLD, 25));
		pwdConfirmPwd.setBounds(155, 130, 250, 30);
		getContentPane().add(pwdConfirmPwd);
		
		txtError = new JLabel();
		txtError.setForeground(Color.RED);
		txtError.setFont(new Font("新宋体", Font.PLAIN, 15));
		txtError.setBounds(155, 173, 250, 30);
		getContentPane().add(txtError);
		
		btnSave = new JButton("确认");
		btnSave.setFont(new Font("宋体", Font.BOLD, 15));
		btnSave.addActionListener(this);
		btnSave.setBounds(60, 216, 100, 30);
		getContentPane().add(btnSave);
		
		btnCancel = new JButton("取消");
		btnCancel.setFont(new Font("宋体", Font.PLAIN, 15));
		btnCancel.addActionListener(this);
		btnCancel.setBounds(250, 216, 100, 30);
		getContentPane().add(btnCancel);

		this.user=user;
	}

	public void actionPerformed(ActionEvent e) {
		try{
			if(e.getSource()==btnSave) {
				if(user.getPassword().equals(pwdOldPwd.getText())){
					if(pwdNewPwd.getText().equals(pwdOldPwd.getText())){
						txtError.setText("新密码不能与原密码相同！");
					}else if(!pwdNewPwd.getText().equals(pwdConfirmPwd.getText())){
						txtError.setText("两次输入的密码不一致请重新输入！");
					}else{
						int result = JOptionPane.showConfirmDialog(this,
								"是否更改密码",
								"系统提醒",
								JOptionPane.YES_NO_OPTION);
						if(result==0){
							if(UserDao.modifyPwd(user,pwdNewPwd.getText())==1){
								JOptionPane.showMessageDialog(this,"密码更改成功");
								user.setPassword(pwdNewPwd.getText());
								this.dispose();
							}else{
								JOptionPane.showMessageDialog(this,
										"密码更改失败，请稍后重试");
							}
						}
					}
				}else{
					txtError.setText("密码错误");
				}

			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
