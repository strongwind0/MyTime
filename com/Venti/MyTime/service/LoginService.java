package com.Venti.MyTime.service;

import com.Venti.MyTime.view.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginService {

    public static WindowAdapter close(JFrame jFrame) {
        return new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                //自定义选择框，“最小化到托盘区”，“退出账号，”退出“
                Object[] options = {"最小化到托盘区","退出该账号","退出程序"};
                int result= JOptionPane.showOptionDialog(null,
                        "请选择操作",
                        "提醒",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if(result==0){
                    //实现最小化至托盘区
                    if(SystemTray.isSupported()){
                        //先关闭
                        jFrame.setVisible(false);
                        //获取系统托盘
                        SystemTray tray = SystemTray.getSystemTray();
                        //设置托盘图标
                        Image image = Toolkit.getDefaultToolkit().getImage("src/image/icon.jpg");

                        //点击menu
                        PopupMenu menu = new PopupMenu();

                        TrayIcon trayIcon = new TrayIcon(image,"MyTime",menu);
                        //设置图片自适应
                        trayIcon.setImageAutoSize(true);

                        MenuItem open = new MenuItem("OPEN");
                        menu.add(open);
                        open.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                //点击open，将界面设为可见，同时从托盘区移除（避免两个界面）
                                if(!jFrame.isShowing()){
                                    jFrame.setVisible(true);
                                    tray.remove(trayIcon);
                                }
                            }
                        });

                        MenuItem exit = new MenuItem("EXIT");
                        menu.add(exit);
                        exit.addActionListener(new ActionListener(){
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // TODO Auto-generated method stub
                                System.exit(0);
                            }
                        });

                        try{
                            //添加到托盘区
                            tray.add(trayIcon);
                        }catch(Exception e1){
                            e1.printStackTrace();
                        }
                    }else{
                        JOptionPane.showMessageDialog(jFrame, "系统不支持");
                    }
                }else if(result==1){
                    //退出当前账号
                    new LoginFrame().setVisible(true);
                    jFrame.dispose();
                }else if(result==2){
                    //退出系统
                    System.exit(0);
                }
            }
        };
    }
}
