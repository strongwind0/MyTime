package com.Venti.MyTime.view.root;

import com.Venti.MyTime.dao.RootDao;
import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.service.RootService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersViewFrame extends JInternalFrame implements ActionListener {
	private JTable table;
	private JTextField txtAccount;
	private JLabel lblAccount;
	private JButton btnQuery;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JButton btnRemove;
	private JButton btnModify;

	/**
	 * Create the frame.
	 */
	public UsersViewFrame() {
		super("用户查询",true,true,true,true);
		setBounds(100, 100, 600, 400);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		lblAccount = new JLabel("账号");
		panel.add(lblAccount);
		
		txtAccount = new JTextField();
		panel.add(txtAccount);
		txtAccount.setColumns(10);
		
		btnQuery = new JButton("查询");
		btnQuery.addActionListener(this);
		panel.add(btnQuery);

		btnRemove = new JButton("删除");
		btnRemove.addActionListener(this);
		panel.add(btnRemove);

		btnModify = new JButton("重置用户密码");
		btnModify.addActionListener(this);
		panel.add(btnModify);

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String[] header = {"用户名","账号","密码","头像路径","创建时间"};
		dtm =new DefaultTableModel(null,header) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(dtm);
		table.setRowHeight(30);
		tableSetting();
		scrollPane.setViewportView(table);

		DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getTableHeader().setDefaultRenderer(renderer);
		table.setDefaultRenderer(Object.class, renderer);

		loadDate();
	}

	public void tableSetting() {
		TableColumn time = table.getColumn("创建时间");
		time.setMinWidth(105);
		time.setMaxWidth(105);
		TableColumn path = table.getColumn("头像路径");
		path.setMaxWidth(190);
		path.setMinWidth(190);
	}

	public void loadDate() {
		dtm.setRowCount(0);
		String userAccount = txtAccount.getText();
		for (User user : RootDao.findUser(userAccount)) {
			dtm.addRow(new Object[]{
					user.getUsername(),
					user.getUserAccountNumber(),
					user.getPassword(),
					user.getPath(),
					user.getCreatedTime()});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			if(e.getSource()==btnRemove){
				int rowIndex = table.getSelectedRow();
				if(rowIndex>=0) {
					String userAccount = (String) table.getValueAt(rowIndex,1);
					RootService.removeUser(userAccount,this);
					loadDate();
				}
			}else if(e.getSource()==btnModify){
				int rowIndex = table.getSelectedRow();
				String userAccount = (String) table.getValueAt(rowIndex,1);
				RootService.resetUserPwd(this,userAccount);
				loadDate();
			}else if(e.getSource()==btnQuery) {
				loadDate();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
