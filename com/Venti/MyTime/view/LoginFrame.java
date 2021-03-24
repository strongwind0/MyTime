package com.Venti.MyTime.view;

import com.Venti.MyTime.dao.RootDao;
import com.Venti.MyTime.dao.UserDao;
import com.Venti.MyTime.entity.Root;
import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.view.root.RootFrame;
import com.Venti.MyTime.view.user.UserFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPwd;
	private JLabel txtError;
	private JRadioButton rdbtnRoot;
	private JRadioButton rdbtnUser;
	private final String imagePath = "/image/login_background.jpg";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//设置为系统样式
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setResizable(false);
		setTitle("MyTime");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 785, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//背景图片
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(LoginFrame.class.getResource(imagePath)));
		lblBackground.setBounds(0, 0, 781, 300);
		contentPane.add(lblBackground);

		//账号
		JLabel lblAccount = new JLabel("Account");
		lblAccount.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblAccount.setBounds(98, 349, 100, 20);
		contentPane.add(lblAccount);

		//密码
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblPassword.setBounds(98, 411, 100, 20);
		contentPane.add(lblPassword);

		//账号框
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Times New Roman", Font.BOLD, 25));
		txtUsername.setBounds(212, 333, 400, 50);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		//点击登录
		JButton btnLogin = new JButton("登录");
		btnLogin.addActionListener(this);
		btnLogin.setFont(new Font("楷体", Font.BOLD, 20));
		btnLogin.setBounds(222, 502, 150, 50);
		contentPane.add(btnLogin);

		//点击退出系统
		JButton butOut = new JButton("注销");
		butOut.setFont(new Font("楷体", Font.BOLD, 20));
		butOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		butOut.setBounds(436, 504, 150, 47);
		contentPane.add(butOut);

		//密码框
		txtPwd = new JPasswordField();
		//键盘监听，回车登录
		txtPwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER) {
					loginCode();
				}
			}
		});
		txtPwd.setFont(new Font("Times New Roman", Font.BOLD, 20));
		txtPwd.setBounds(212, 396, 400, 50);
		contentPane.add(txtPwd);

		//登录错误信息提醒
		txtError = new JLabel("");
		txtError.setForeground(Color.RED);
		txtError.setBounds(212, 459, 215, 18);
		contentPane.add(txtError);

		/**
		 * 设置单选按钮，便于区分登录账户
		 */
		rdbtnRoot = new JRadioButton("管理员");
		rdbtnRoot.setBounds(539, 455, 73, 27);
		contentPane.add(rdbtnRoot);

		rdbtnUser = new JRadioButton("普通用户");
		rdbtnUser.setBounds(444, 455, 89, 27);
		contentPane.add(rdbtnUser);

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnUser);
		btnGroup.add(rdbtnRoot);
	}

	/**
	 * 监听器
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		loginCode();
	}

	/**
	 * 登录判断代码块
	 */
	private void loginCode() {
		String accountNumber = txtUsername.getText();
		String password = txtPwd.getText();
		User user = UserDao.login(accountNumber, password);
		Root root = RootDao.login(accountNumber,password);
		if(rdbtnRoot.isSelected()==rdbtnUser.isSelected()){
			txtError.setText("请选择账户类型");
		} else if(user.getUserAccountNumber()==null&&root.getRootAccountNumber()==null) {
			txtError.setText("账号或密码错误");
		}else if (rdbtnUser.isSelected() && user.getUserAccountNumber()!=null){
			new UserFrame(user).setVisible(true);
			this.dispose();
		}else if(rdbtnRoot.isSelected() && root.getRootAccountNumber()!=null){
			new RootFrame(root).setVisible(true);
			this.dispose();
		}else {
			txtError.setText("账户类型选择错误");
		}
	}
}
