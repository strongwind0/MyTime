package com.Venti.MyTime.view.user;

import com.Venti.MyTime.dao.AffairDao;
import com.Venti.MyTime.dao.MusicDao;
import com.Venti.MyTime.entity.Affair;
import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.service.LoginService;
import com.Venti.MyTime.service.UserService;
import com.Venti.MyTime.utils.MusicPlayerUtils;
import com.Venti.MyTime.view.AboutFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.JOptionPane.*;

public class UserFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel lblUserAvatar;
	private JDesktopPane desktopPane;
	private JLabel lblUsername;
	private JPanel panel;
	private JLabel lblTime;
	private JButton btnGetThing;
	private JButton btnFinish;
	private JMenuItem mntmTransactionQuery;
	private JMenuItem mntmTransactionAdd;
	private JMenuItem mntmModify;
	private JMenuItem mntmView;
	private JMenuItem mntmModifyPassword;
	private JMenuItem mntmVersion;
	private JLabel lblNowThing;
	private JLabel lblNowPlace;
	private JLabel lblChooseMusic;
	private JComboBox comboBox;
	private JButton btnStopMusic;

	private User user;
	private String music;
	private Affair affair;
	private Thread musicPlayer;

	/**
	 * Create the frame.
	 */
	public UserFrame(User user) {
		this.user = user;

		setTitle("普通用户");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//窗口关闭监听
		addWindowListener(LoginService.close(this));
		setBounds(100, 100, 900, 600);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnThingManager = new JMenu("事务管理");
		menuBar.add(mnThingManager);

		mntmTransactionQuery = new JMenuItem("事务查询");
		mntmTransactionQuery.addActionListener(this);
		mnThingManager.add(mntmTransactionQuery);
		
		mntmTransactionAdd = new JMenuItem("事务添加");
		mntmTransactionAdd.addActionListener(this);
		mnThingManager.add(mntmTransactionAdd);

		mntmModify = new JMenuItem("事务修改");
		mntmModify.addActionListener(this);
		mnThingManager.add(mntmModify);
		
		JMenu mnMyInformation = new JMenu("我的");
		menuBar.add(mnMyInformation);
		
		mntmView = new JMenuItem("查看");
		mntmView.addActionListener(this);
		mnMyInformation.add(mntmView);

		mntmModifyPassword = new JMenuItem("修改密码");
		mntmModifyPassword.addActionListener(this);
		mnMyInformation.add(mntmModifyPassword);
		
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
		
		lblUserAvatar = new JLabel("",JLabel.CENTER);
		lblUserAvatar.setBounds(20, 45, 225, 300);
		lblUserAvatar.setIcon(new ImageIcon(UserFrame.class.getResource(user.getPath())));
		desktopPane.add(lblUserAvatar);
		
		lblUsername = new JLabel(user.getUsername(),JLabel.CENTER);
		lblUsername.setFont(new Font("宋体", Font.BOLD, 35));
		lblUsername.setBounds(20, 350, 225, 50);
		desktopPane.add(lblUsername);
		
		lblTime = new JLabel("",JLabel.CENTER);
		lblTime.setFont(new Font("Times New Roman", Font.BOLD, 55));
		lblTime.setBounds(300, 40, 500, 100);
		configTimeArea();
		desktopPane.add(lblTime);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null,
				"\u5F53\u524D\u4E8B\u52A1",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				null,
				null));
		panel.setBounds(300, 175, 500, 325);
		desktopPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblThing = new JLabel("事务",JLabel.CENTER);
		lblThing.setFont(new Font("宋体", Font.BOLD, 25));
		lblThing.setBounds(20, 35, 100, 50);
		panel.add(lblThing);
		
		JLabel lblPlace = new JLabel("地点", SwingConstants.CENTER);
		lblPlace.setAutoscrolls(true);
		lblPlace.setFont(new Font("宋体", Font.BOLD, 25));
		lblPlace.setBounds(20, 140, 100, 50);
		panel.add(lblPlace);
		
		lblNowThing = new JLabel("");
		lblNowThing.setBounds(150, 35, 300, 100);
		lblNowThing.setFont(new Font("楷体", Font.BOLD,25));
		panel.add(lblNowThing);
		
		lblNowPlace = new JLabel("");
		lblNowPlace.setFont(new Font("楷体", Font.BOLD,25));
		lblNowPlace.setBounds(150, 140, 300, 100);
		panel.add(lblNowPlace);

		btnGetThing = new JButton("获取最近事务");
		btnGetThing.addActionListener(this);
		btnGetThing.setBounds(30,250,125,50);
		panel.add(btnGetThing);

		btnStopMusic = new JButton("停止音乐");
		btnStopMusic.addActionListener(this);
		btnStopMusic.setBounds(190, 250, 125, 50);
		panel.add(btnStopMusic);
		
		btnFinish = new JButton("完成该事务");
		btnFinish.addActionListener(this);
		btnFinish.setBounds(350, 250, 125, 50);
		panel.add(btnFinish);

		lblChooseMusic = new JLabel("请选择提醒歌曲");
		lblChooseMusic.setFont(new Font("宋体", Font.BOLD, 15));
		lblChooseMusic.setBounds(15,420,250,40);
		desktopPane.add(lblChooseMusic);

		comboBox = new JComboBox();
		comboBox.setFont(new Font("微软雅黑", Font.BOLD, 20));
		comboBox.setBounds(15,450,250,50);
		for(String music: MusicDao.getMusicList()){
			comboBox.addItem(music);
		}
		comboBox.addActionListener(this);
		desktopPane.add(comboBox);
	}

	/**
	 * 动态显示时间
	 */
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
			lblTime.setText(time);
			reminder();
		}
	}

	public void reminder() {
		if(affair !=null) {
			if(lblTime.getText().equals(affair.getStartTime())){
				musicPlayer = new Thread(new MusicPlayerUtils("src/music/"+comboBox.getSelectedItem()));
				musicPlayer.start();
				JOptionPane.showMessageDialog(this,
						"开始时间到了，快开始你的事务吧！");

			}
			if(lblTime.getText().equals(affair.getEndTime())){
				musicPlayer = new Thread(new MusicPlayerUtils("src/music/"+comboBox.getSelectedItem()));
				musicPlayer.start();
				//点确定，执行完成代码，否，将数据库里的状态改为-1，表中显示未完成
				JOptionPane.showMessageDialog(this,"事务结束了，你完成了吗？");
			}
		}
	}

	/**
	 * 对输入到”要做的事“和”地点“的框里的文字进行分行,还需要优化
	 * @param primeval
	 * @return
	 */
	public String setJLabelText(String primeval) {
		StringBuffer stringBuffer = new StringBuffer(primeval);
		if(primeval.length()>20){
			stringBuffer.insert(19, "<br>");
		}
		if(primeval.length() > 10){
			stringBuffer.insert(9, "<br>");
		}
		primeval ="<html>" + stringBuffer.toString()+"</html>";
		return primeval;
	}

	/**
	 * 监听器
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		try{
			if (e.getSource() == mntmTransactionAdd){
				AffairsAddFrame affairsAddFrame = new AffairsAddFrame(this.user.getUserAccountNumber());
				affairsAddFrame.setVisible(true);
				desktopPane.add(affairsAddFrame);
			}else if(e.getSource()==mntmVersion) {
				AboutFrame aboutFrame = new AboutFrame();
				aboutFrame.setVisible(true);
				desktopPane.add(aboutFrame);
			}else if(e.getSource()==mntmView) {
				UserViewFrame userViewFrame = new UserViewFrame(user);
				userViewFrame.setVisible(true);
				desktopPane.add(userViewFrame);
			} else if(e.getSource()==mntmModifyPassword) {
				UserModifyPwdFrame userModifyPwdFrame = new UserModifyPwdFrame(user);
				userModifyPwdFrame.setVisible(true);
				desktopPane.add(userModifyPwdFrame);
			}else if(e.getSource()==mntmTransactionQuery) {
				AffairsViewFrame affairsViewFrame = new AffairsViewFrame(user.getUserAccountNumber());
				affairsViewFrame.setVisible(true);
				desktopPane.add(affairsViewFrame);
			}else if(e.getSource()==mntmModify) {
				AffairsModifyFrame affairsModifyFrame = new AffairsModifyFrame(user.getUserAccountNumber());
				affairsModifyFrame.setVisible(true);
				desktopPane.add(affairsModifyFrame);
			}else if(e.getSource()==btnGetThing) {
				int totalOfTimeout = UserService.timeout(user.getUserAccountNumber());
				if(totalOfTimeout>0){
					JOptionPane.showMessageDialog(this,"你有"+totalOfTimeout+"件事务超时了，请前往修改");
				}
				affair = AffairDao.findAffairs(user.getUserAccountNumber(),
						"").stream().findFirst().orElse(null);
				if(affair ==null) {
					lblNowPlace.setText("");
					lblNowThing.setText("");
					showMessageDialog(this,"当前没有事务，请先添加事务");
				}else{
					lblNowPlace.setText(setJLabelText(affair.getPlace()));
					lblNowThing.setText(setJLabelText(affair.getThing()));
				}
			}else if (e.getSource()==btnFinish) {
				if(!(lblNowThing.getText()!=null&& lblNowThing.equals("")
						&& lblNowPlace.getText()!=null&&lblNowPlace.getText().equals(""))) {
					int result = showConfirmDialog(this,
							"确定完成",
							"系统提醒",
							YES_NO_OPTION);
					if(result== YES_OPTION) {
						int row = AffairDao.finishAffair(user.getUserAccountNumber(), affair.getID());
						if(row==1) {
							lblNowPlace.setText("");
							lblNowThing.setText("");
							//需要自动加载下一个事务（先检查有无，在载入，可把手动加载用的抽离成方法）
							showMessageDialog(this,"事务完成");
						}else {
							showMessageDialog(this,"事务完成失败，请稍后重试");
						}

					}
				}else {
					showMessageDialog(this,"请先点击\"获取最近事务\"");
				}
			}else if(e.getSource()==comboBox) {
				music ="src/music/"+(String) comboBox.getSelectedItem();
			}else if(e.getSource()==btnStopMusic) {
				if(musicPlayer!=null) {
					if(musicPlayer.isAlive()){
						musicPlayer.stop();
					}
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
}
