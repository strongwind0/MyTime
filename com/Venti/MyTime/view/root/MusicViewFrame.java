package com.Venti.MyTime.view.root;

import com.Venti.MyTime.dao.MusicDao;
import com.Venti.MyTime.entity.Music;
import com.Venti.MyTime.service.RootService;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MusicViewFrame extends JInternalFrame implements ActionListener {
	private JTable table;
	private JTextField txtFind;
	private JButton btnFind;
	private JButton btnDelete;
	private DefaultTableModel dtm;

	/**
	 * Create the frame.
	 */
	public MusicViewFrame() {
		super("查看音乐",true,true,true,true);
		setBounds(100, 100, 600, 400);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		txtFind = new JTextField();
		panel.add(txtFind);
		txtFind.setColumns(10);
		
		btnFind = new JButton("查询");
		btnFind.addActionListener(this);
		panel.add(btnFind);
		
		btnDelete = new JButton("删除");
		btnDelete.addActionListener(this);
		panel.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		String[] header = {"ID","Music"};
		dtm = new DefaultTableModel(null,header) {
			@Override
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		
		table = new JTable(dtm);
		tableSetting();
		scrollPane.setViewportView(table);

		loadDate();

	}
	
	public void tableSetting() {
		TableColumn ID = table.getColumn("ID");
		ID.setMaxWidth(100);
		ID.setMinWidth(100);
	}

	public void loadDate() {
		dtm.setRowCount(0);
		String musicName = txtFind.getText();
		for(Music music : MusicDao.findMusic(musicName)) {
			dtm.addRow(new Object[]{
					music.getID(),
					music.getMusicName()});
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnFind) {
			loadDate();
		}else if(e.getSource()==btnDelete) {
			int rowIndex = table.getSelectedRow();
			if(rowIndex>=0) {
				String musicName = (String) table.getValueAt(rowIndex,1);
				RootService.removeMusic(musicName,this);
			}
			loadDate();
		}
	}
}
