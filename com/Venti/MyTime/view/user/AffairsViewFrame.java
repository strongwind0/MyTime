package com.Venti.MyTime.view.user;

import com.Venti.MyTime.dao.AffairDao;
import com.Venti.MyTime.entity.Affair;
import com.Venti.MyTime.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AffairsViewFrame extends JInternalFrame implements ActionListener {
	private JTable table;
	private JTextField txtID;
	private JButton btnFind;
	private JButton btnRemove;
	private JButton btnFinish;
	private DefaultTableModel dtm;

	private String userAccount;

	/**
	 * Create the frame.
	 */
	public AffairsViewFrame(String userAccount) {
		super("TimeTable",true,true,true,true);
		setBounds(100, 100, 600, 400);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblID = new JLabel("任务ID");
		panel.add(lblID);
		
		txtID = new JTextField();
		panel.add(txtID);
		txtID.setColumns(10);
		
		btnFind = new JButton("查询");
		btnFind.addActionListener(this);
		panel.add(btnFind);
		
		btnRemove = new JButton("删除");
		btnRemove.addActionListener(this);
		panel.add(btnRemove);

		btnFinish = new JButton("完成任务");
		btnFinish.addActionListener(this);
		panel.add(btnFinish);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		String[] header = {"ID","开始时间","结束时间","地点","事务","状态"};
		dtm = new DefaultTableModel(null,header) {
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

		this.userAccount = userAccount;

		loadDate();

	}

	public void tableSetting() {
		//设置ID列宽度
		TableColumn ID = table.getColumn("ID");
		ID.setMaxWidth(110);
		ID.setMinWidth(110);
		//设置开始时间宽度
		TableColumn start = table.getColumn("开始时间");
		start.setMinWidth(125);
		start.setMaxWidth(125);
		//设置结束时间宽度
		TableColumn end = table.getColumn("结束时间");
		end.setMaxWidth(125);
		end.setMinWidth(125);
		//设置状态列宽度，字体颜色
		TableColumn state = table.getColumn("状态");
		DefaultTableCellRenderer startRenderer = new DefaultTableCellRenderer();
		startRenderer.setForeground(Color.RED);
		startRenderer.setHorizontalAlignment(JLabel.CENTER);
		state.setCellRenderer(startRenderer);
		state.setMinWidth(60);
		state.setMaxWidth(60);
	}

	public void loadDate() {
		dtm.setRowCount(0);
		String ID = txtID.getText();
		for(Affair affair : AffairDao.findAffairs(userAccount,ID)){
			dtm.addRow(new Object[]{
					affair.getID(),
					affair.getStartTime(),
					affair.getEndTime(),
					affair.getPlace(),
					affair.getThing(),
					affair.getState() ==0 ? "未做" : "超时"});
		}
	}

	public void actionPerformed(ActionEvent e) {
		try{
			if(e.getSource()==btnFind) {
				loadDate();
			}else if(e.getSource()==btnRemove) {
				int rowIndex = table.getSelectedRow();
				if(rowIndex>=0) {
					String ID = (String) table.getValueAt(rowIndex,0);
					UserService.remove(userAccount,ID,this);
				}
				loadDate();
			} else if(e.getSource()==btnFinish){
				int rowIndex = table.getSelectedRow();
				if(rowIndex >= 0) {
					String ID = (String) table.getValueAt(rowIndex,0);
					UserService.finish(userAccount,ID,this);
				}
				 loadDate();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
