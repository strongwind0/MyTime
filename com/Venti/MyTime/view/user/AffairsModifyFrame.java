package com.Venti.MyTime.view.user;

import com.Venti.MyTime.dao.AffairDao;
import com.Venti.MyTime.entity.Affair;
import com.Venti.MyTime.service.UserService;
import com.mkk.swing.JTimeChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class AffairsModifyFrame extends JInternalFrame implements ActionListener {
	private JTextField txtStartTime;
	private JTextField txtEndTime;
	private JTextField txtPlace;
	private JTextField txtThing;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnChooseEnd;
	private JButton btnChooseStart;
	private JTextField txtFind;
	private JButton btnFind;
	private JTimeChooser JTime;
	private JLabel lblError;

	private String userAccount;
	private final String timeFormat="\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d \\d\\d\\:\\d\\d\\:\\d\\d";
	private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Create the frame.
	 */
	public AffairsModifyFrame(String userAccount) {
		super("修改任务",true,true,false,true);
		setBounds(100, 100, 750, 500);
		getContentPane().setLayout(null);
		
		JLabel lblStartTime = new JLabel("开始时间");
		lblStartTime.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblStartTime.setBounds(41, 100, 150, 40);
		getContentPane().add(lblStartTime);
		
		JLabel lblEndTime = new JLabel("结束时间");
		lblEndTime.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblEndTime.setBounds(41, 180, 150, 40);
		getContentPane().add(lblEndTime);
		
		JLabel lblPlace = new JLabel("地点");
		lblPlace.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblPlace.setBounds(41, 240, 150, 40);
		getContentPane().add(lblPlace);
		
		JLabel lblThing = new JLabel("要做的事");
		lblThing.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblThing.setBounds(41, 299, 150, 40);
		getContentPane().add(lblThing);

		txtStartTime = new JTextField();
		txtStartTime.setFont(new Font("Times New Roman", Font.BOLD, 25));
		txtStartTime.setBounds(205, 100, 365, 50);
		getContentPane().add(txtStartTime);
		txtStartTime.setColumns(10);
		
		txtEndTime = new JTextField();
		txtEndTime.setFont(new Font("Times New Roman", Font.BOLD, 25));
		txtEndTime.setText("");
		txtEndTime.setBounds(205, 165, 365, 50);
		getContentPane().add(txtEndTime);
		txtEndTime.setColumns(10);
		
		txtPlace = new JTextField();
		txtPlace.setFont(new Font("楷体", Font.PLAIN, 25));
		txtPlace.setBounds(205, 235, 365, 50);
		getContentPane().add(txtPlace);
		txtPlace.setColumns(10);
		
		txtThing = new JTextField();
		txtThing.setFont(new Font("楷体", Font.PLAIN, 25));
		txtThing.setBounds(205, 300, 365, 50);
		getContentPane().add(txtThing);
		txtThing.setColumns(10);
		
		btnChooseStart = new JButton("选择开始时间");
		btnChooseStart.addActionListener(this);
		btnChooseStart.setBounds(584, 110, 125, 30);
		getContentPane().add(btnChooseStart);
		
		btnChooseEnd = new JButton("选择结束时间");
		btnChooseEnd.addActionListener(this);
		btnChooseEnd.setBounds(584, 175, 125, 30);
		getContentPane().add(btnChooseEnd);
		
		btnSave = new JButton("保存");
		btnSave.setFont(new Font("微软雅黑", Font.BOLD, 25));
		btnSave.addActionListener(this);
		btnSave.setBounds(151, 387, 150, 50);
		getContentPane().add(btnSave);
		
		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(451, 387, 120, 50);
		getContentPane().add(btnCancel);
		
		txtFind = new JTextField();
		txtFind.setBounds(165, 13, 277, 50);
		getContentPane().add(txtFind);
		txtFind.setColumns(10);
		
		btnFind = new JButton("查找");
		btnFind.addActionListener(this);
		btnFind.setBounds(475, 25, 113, 27);
		getContentPane().add(btnFind);

		lblError = new JLabel("");
		lblError.setBounds(205,355,150,30);
		lblError.setForeground(Color.RED);
		getContentPane().add(lblError);

		this.userAccount = userAccount;
	}

	public void actionPerformed(ActionEvent e) {
		try{
			//需确定修改后的时间不冲突，事务地点的字数限制
			String ID = txtFind.getText();
			if (e.getSource() == btnFind) {
				Affair affair = AffairDao.preciseSearchAffair(userAccount, ID);
				if(affair !=null) {
					txtStartTime.setText(affair.getStartTime());
					txtEndTime.setText(affair.getEndTime());
					txtPlace.setText(affair.getPlace());
					txtThing.setText(affair.getThing());
				}else {
					JOptionPane.showMessageDialog(this,"事务不存在，请确认事务ID");
				}
			} else if (e.getSource() == btnChooseStart) {
				TimeFrame timeFrame = new TimeFrame();
				JTime = new JTimeChooser(timeFrame);
				txtStartTime.setText(fmt.format(JTime.showTimeDialog().getTime()));
			} else if (e.getSource() == btnChooseEnd) {
				TimeFrame timeFrame = new TimeFrame();
				JTime = new JTimeChooser(timeFrame);
				txtEndTime.setText(fmt.format(JTime.showTimeDialog().getTime()));
			} else if (e.getSource() == btnSave) {
				Affair affair = new Affair(txtFind.getText(),txtStartTime.getText(),
						txtEndTime.getText(),
						txtPlace.getText(),
						txtThing.getText());
				int result = UserService.savaAffair(this, userAccount, affair,1);
				switch (result) {
					case -1 : lblError.setText("请选择开始时间");break;
					case -2 : lblError.setText("请选择结束时间");break;
					case -3 : lblError.setText("请输入地点");break;
					case -4 : lblError.setText("请输入事务");break;
					case -5 : lblError.setText("时间格式错误，请重新选择时间");break;
					case 1  : txtPlace.setText("");txtThing.setText("");
					case 0  : txtStartTime.setText("");txtEndTime.setText("");break;
					default:lblError.setText("");break;
				}
			}
			else{
				this.dispose();
			}
		} catch (HeadlessException headlessException) {
			headlessException.printStackTrace();
		}
	}

}
