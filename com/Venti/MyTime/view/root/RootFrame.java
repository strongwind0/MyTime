package com.Venti.MyTime.view.root;

import com.Venti.MyTime.entity.Root;
import com.Venti.MyTime.service.LoginService;
import com.Venti.MyTime.view.AboutFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RootFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JLabel lblNowTime;
	private JLabel lblRootName;
	private JLabel lblAvatar;
	private JMenuItem mntmUserQuery;
	private JMenuItem mntmUserAdd;
	private JMenuItem mntmView;
	private JMenuItem mntmModifyPwd;
	private JMenuItem mntmVersion;
	private JMenuItem mntmMusicAdd;
	private JMenuItem mntmMusicQuery;
	private JDesktopPane desktopPane;


	private Root root;

	//添加一个按钮，添加音乐的功能（参考修改头像代码，只需保留名字.mp3）

	/**
	 * Create the frame.
	 */
	public RootFrame(Root root) {
		setTitle("管理员");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//窗口关闭监听
		addWindowListener(LoginService.close(this));
		setBounds(100, 100, 900, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnUserManager = new JMenu("用户管理");
		menuBar.add(mnUserManager);

		mntmUserQuery = new JMenuItem("用户查询");
		mntmUserQuery.addActionListener(this);
		mnUserManager.add(mntmUserQuery);
		
		mntmUserAdd = new JMenuItem("用户添加");
		mntmUserAdd.addActionListener(this);
		mnUserManager.add(mntmUserAdd);

		JMenu mnMusic = new JMenu("音乐");
		menuBar.add(mnMusic);

		mntmMusicQuery = new JMenuItem("音乐查询");
		mntmMusicQuery.addActionListener(this);
		mnMusic.add(mntmMusicQuery);

		mntmMusicAdd = new JMenuItem("音乐添加");
		mntmMusicAdd.addActionListener(this);
		mnMusic.add(mntmMusicAdd);
		
		JMenu mnMyInformation = new JMenu("我的");
		menuBar.add(mnMyInformation);
		
		mntmView = new JMenuItem("查看");
		mntmView.addActionListener(this);
		mnMyInformation.add(mntmView);
		
		mntmModifyPwd = new JMenuItem("修改密码");
		mntmModifyPwd.addActionListener(this);
		mnMyInformation.add(mntmModifyPwd);
		
		JMenu mnAbout = new JMenu("关于");
		menuBar.add(mnAbout);
		
		mntmVersion = new JMenuItem("版本");
		mntmVersion.addActionListener(this);
		mnAbout.add(mntmVersion);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		desktopPane = new JDesktopPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);
		desktopPane.setLayout(null);
		
		lblAvatar = new JLabel("");
		lblAvatar.setBounds(45, 45, 225, 300);
		lblAvatar.setIcon(new ImageIcon(RootFrame.class.getResource(root.getPath())));
		desktopPane.add(lblAvatar);
		
		lblRootName = new JLabel(root.getRootName(),JLabel.CENTER);
		lblRootName.setFont(new Font("微软雅黑", Font.BOLD, 35));
		lblRootName.setBounds(45, 380, 225, 50);
		desktopPane.add(lblRootName);
		
		lblNowTime = new JLabel("");
		lblNowTime.setFont(new Font("Times New Roman", Font.BOLD, 55));
		lblNowTime.setBounds(315, 150, 500, 100);
		configTimeArea();
		desktopPane.add(lblNowTime);

		this.root=root;
	}

	private void configTimeArea() {
		// TODO Auto-generated method stub
		long ONE_SECOND =1000;
		Timer timer  =new Timer();
		timer.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
		}
		
	protected class JLabelTimerTask extends TimerTask {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = fmt.format(Calendar.getInstance().getTime());
			lblNowTime.setText(time);
		}
	}
	
	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource()==mntmVersion){
				AboutFrame aboutFrame = new AboutFrame();
				aboutFrame.setVisible(true);
				desktopPane.add(aboutFrame);
			}else if(e.getSource()==mntmModifyPwd) {
				RootModifyPwdFrame rootModifyPwdFrame = new RootModifyPwdFrame(root);
				rootModifyPwdFrame.setVisible(true);
				desktopPane.add(rootModifyPwdFrame);
			}else if(e.getSource()==mntmView) {
				RootViewFrame rootViewFrame = new RootViewFrame(root);
				rootViewFrame.setVisible(true);
				desktopPane.add(rootViewFrame);
			}else if(e.getSource()==mntmUserAdd) {
				UserAddFrame userAddFrame = new UserAddFrame();
				userAddFrame.setVisible(true);
				desktopPane.add(userAddFrame);
			}else if(e.getSource()==mntmUserQuery){
				UsersViewFrame usersViewFrame = new UsersViewFrame();
				usersViewFrame.setVisible(true);
				desktopPane.add(usersViewFrame);
			}else if(e.getSource()==mntmMusicAdd) {
				MusicAddFrame musicAddFrame = new MusicAddFrame();
				musicAddFrame.setVisible(true);
				desktopPane.add(musicAddFrame);
			}else if(e.getSource()==mntmMusicQuery) {
				MusicViewFrame musicViewFrame = new MusicViewFrame();
				musicViewFrame.setVisible(true);
				desktopPane.add(musicViewFrame);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
