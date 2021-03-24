package com.Venti.MyTime.view.user;

import com.Venti.MyTime.entity.Affair;
import com.Venti.MyTime.service.UserService;
import com.mkk.swing.JTimeChooser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class AffairsAddFrame extends JInternalFrame implements ActionListener {
	private JTextField txtStartTime;
	private JTextField txtEndTime;
	private JTextField txtPlace;
	private JTextField txtThing;
	private JButton btnConfirm;
	private JButton btnCancel;
	private JButton btnStart;
	private JButton btnEnd;
	private JTimeChooser JTime;
	private JLabel lblError;

	private String userAccount;
	private final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Create the frame.
	 */
	public AffairsAddFrame(String userAccount) {
		super("添加事务",true,true,false,true);
		//setTitle("事务添加");
		setBounds(100, 100, 750, 500);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				null,
				"\u6DFB\u52A0\u4E8B\u52A1",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				null,
				null));
		panel.setBounds(14, 13, 706, 438);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblStarttime = new JLabel("起始时间");
		lblStarttime.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblStarttime.setBounds(41, 55, 150, 40);
		panel.add(lblStarttime);
		
		JLabel lblEndTime = new JLabel("结束时间");
		lblEndTime.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblEndTime.setBounds(41, 128, 150, 40);
		panel.add(lblEndTime);
		
		JLabel lblOlace = new JLabel("地点");
		lblOlace.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblOlace.setBounds(41, 206, 150, 40);
		panel.add(lblOlace);
		
		JLabel lblThing = new JLabel("事件");
		lblThing.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblThing.setBounds(41, 277, 150, 40);
		panel.add(lblThing);
		
		btnConfirm = new JButton("确认");
		btnConfirm.addActionListener(this);
		btnConfirm.setForeground(Color.ORANGE);
		btnConfirm.setFont(new Font("黑体", Font.BOLD, 25));
		btnConfirm.setBounds(113, 360, 150, 45);
		panel.add(btnConfirm);
		
		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setForeground(Color.RED);
		btnCancel.setBounds(388, 360, 150, 45);
		panel.add(btnCancel);

		txtStartTime = new JTextField();
		txtStartTime.setBounds(205, 55, 365, 50);
		panel.add(txtStartTime);
		txtStartTime.setColumns(10);
		
		txtEndTime = new JTextField();
		txtEndTime.setColumns(10);
		txtEndTime.setBounds(205, 128, 365, 50);
		panel.add(txtEndTime);
		
		txtPlace = new JTextField();
		txtPlace.setColumns(10);
		txtPlace.setBounds(205, 206, 365, 50);
		panel.add(txtPlace);
		
		txtThing = new JTextField();
		txtThing.setColumns(10);
		txtThing.setBounds(205, 277, 365, 50);
		panel.add(txtThing);

		lblError = new JLabel();
		lblError.setBounds(205,330,300,25);
		lblError.setForeground(Color.RED);
		panel.add(lblError);
		
		btnStart = new JButton("选择时间");
		btnStart.addActionListener(this);
		btnStart.setBounds(584, 69, 108, 27);
		panel.add(btnStart);
		
		btnEnd = new JButton("选择时间");
		btnEnd.addActionListener(this);
		btnEnd.setBounds(584, 142, 108, 27);
		panel.add(btnEnd);

		this.userAccount = userAccount;

	}
	
	public void actionPerformed(ActionEvent e) {
		//事务的地点和事件输入的字数等限制
		try {
			if (e.getSource() == btnConfirm) {
				Affair affair = new Affair(txtStartTime.getText(),
						txtEndTime.getText(),
						txtPlace.getText(),
						txtThing.getText());
				int result = UserService.savaAffair(this, userAccount, affair,0);
				switch (result) {
					case -1 : lblError.setText("请选择开始时间");break;
					case -2 : lblError.setText("请选择结束时间");break;
					case -3 : lblError.setText("请输入地点");break;
					case -4 : lblError.setText("请输入事务");break;
					case -5 : lblError.setText("时间格式错误，请重新选择时间");break;
					case 1  : txtPlace.setText("");txtThing.setText("");
					case 0  : txtStartTime.setText("");txtEndTime.setText("");break;
				}
			} else if (e.getSource() == btnCancel) {
				this.dispose();
			} else if (e.getSource() == btnStart) {
				TimeFrame timeFrame = new TimeFrame();
				JTime = new JTimeChooser(timeFrame, "请选择开始时间");
				txtStartTime.setText(fmt.format(JTime.showTimeDialog().getTime()));
			} else if (e.getSource() == btnEnd) {
				TimeFrame timeFrame = new TimeFrame();
				JTime = new JTimeChooser(timeFrame, "请选择结束时间");
				txtEndTime.setText(fmt.format(JTime.showTimeDialog().getTime()));
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
