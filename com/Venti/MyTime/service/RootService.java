package com.Venti.MyTime.service;


import com.Venti.MyTime.dao.MusicDao;
import com.Venti.MyTime.dao.RootDao;
import com.Venti.MyTime.entity.Root;
import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.utils.CommonUtils;
import com.Venti.MyTime.view.root.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RootService {

    private static File imageFile;
    private static String imageFilePath;
    private static String musicFilePath;

    public static String chooserAvatar(RootViewFrame rootViewFrame) {
        JFileChooser jFileChooser = new JFileChooser("C:\\Users\\长风破浪\\Desktop");
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.getName().endsWith(".jpg") || f.getName().endsWith(".png")){
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        int result = jFileChooser.showOpenDialog(rootViewFrame);
        if(result==JFileChooser.APPROVE_OPTION){
            imageFile = jFileChooser.getSelectedFile();
            imageFilePath = imageFile.getAbsolutePath();
        }
        return imageFilePath;
    }

    public static String saveRoot(Root root, String name, String imageFilePath, RootViewFrame rootViewFrame)  {
        String relativePath = imageFilePath.substring(imageFilePath.lastIndexOf("\\"));
        relativePath = "/image/"+
                CommonUtils.getFileName()+
                relativePath.substring(relativePath.indexOf("."));
        int result = JOptionPane.showConfirmDialog(rootViewFrame,
                "是否保存",
                "系统提醒",
                JOptionPane.YES_NO_OPTION);
        if(result==0) {
            if(RootDao.modifyRoot(root,name,relativePath)==1){
                try{
                    FileInputStream fis = new FileInputStream(imageFilePath);
                    FileOutputStream fos = new FileOutputStream("src"+relativePath);
                    byte[] buff = new byte[2048];
                    int len = 0;
                    while((len=fis.read(buff))>0) {
                        fos.write(buff,0,len);
                    }
                    fis.close();
                    fos.close();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(rootViewFrame,"保存成功");
                root.setRootName(name);
                root.setPath(relativePath);

            }else{
                JOptionPane.showMessageDialog(rootViewFrame, "保存失败，请稍后重试");
            }
        }
        return relativePath;
    }

    public static String modifyPwd(Root root,
                                   String oldPwd,
                                   String newPwd,
                                   String confirmPwd,
                                   RootModifyPwdFrame rootModifyPwdFrame) {
        if(oldPwd.equals("")) {
            JOptionPane.showMessageDialog(rootModifyPwdFrame,
                    "不得在三个密码栏输入空字符串",
                    "警告！",
                    JOptionPane.ERROR_MESSAGE);
        }
        if(root.getPassword().equals(oldPwd)){
            if(newPwd.equals(oldPwd)){
                return  "新密码不能与原密码相同！";
            }else if(!newPwd.equals(confirmPwd)){
                return "两次输入的密码不一致请重新输入！";
            }else{
                int result = JOptionPane.showConfirmDialog(rootModifyPwdFrame,
                        "是否更改密码",
                        "系统提醒",
                        JOptionPane.YES_NO_OPTION);
                if(result==JOptionPane.YES_OPTION){
                    int row = RootDao.modifyRootPwd(root,newPwd);
                    if(row==1) {
                        JOptionPane.showMessageDialog(rootModifyPwdFrame,"密码修改成功");
                        return "";
                    }else{
                        JOptionPane.showMessageDialog(rootModifyPwdFrame,"密码修改失败，请稍后重试");
                    }
                }
            }
        }else{
            return "密码错误";
        }
        return "";
    }

    public static int saveUser(UserAddFrame userAddFrame, User user) {
        int result = JOptionPane.showConfirmDialog(userAddFrame,
                "确认保存？",
                "系统提醒",
                JOptionPane.YES_NO_OPTION);
        if(result==0) {
            boolean doesItExist = false;
            for(User u:RootDao.findUser("")) {
                doesItExist = u.getUserAccountNumber().equalsIgnoreCase(user.getUserAccountNumber());
                if(doesItExist)
                    break;
            }
            if(!doesItExist){
                if (RootDao.createUser(user) == 1) {
                    JOptionPane.showMessageDialog(userAddFrame,
                            "添加用户成功");
                    return 1;
                } else {
                    JOptionPane.showMessageDialog(userAddFrame,
                            "添加用户失败，请稍后重试");
                    return 0;
                }
            }else{
                JOptionPane.showMessageDialog(userAddFrame,
                        "该账号已存在，请重新输入");
                return -1;
            }
        }
        return 0;
    }

    public static void removeUser(String userAccount, UsersViewFrame usersViewFrame){
        int result = JOptionPane.showConfirmDialog(usersViewFrame,
                "确认删除？",
                "系统提醒",
                JOptionPane.YES_NO_OPTION);
        if(result==JOptionPane.YES_OPTION){
            int row = RootDao.removeUser(userAccount);
            if(row == 1) {
                JOptionPane.showMessageDialog(usersViewFrame,
                        "删除成功！");
            }else{
                JOptionPane.showMessageDialog(usersViewFrame,
                        "删除失败！");
            }
        }
    }

    public static void resetUserPwd(UsersViewFrame usersViewFrame,String userAccount) {
        int row = RootDao.resetUserPwd(userAccount);
        if(row==1){
            JOptionPane.showMessageDialog(usersViewFrame,"用户密码重置成功");
        }else {
            JOptionPane.showMessageDialog(usersViewFrame,"用户密码重置失败");
        }
    }

    public static String chooserMusic(MusicAddFrame musicAddFrame) {
        JFileChooser jFileChooser = new JFileChooser("C:\\Users\\长风破浪\\Desktop");
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if(f.getName().endsWith(".mp3")){
                    return true;
                }
                return false;
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        int result = jFileChooser.showOpenDialog(musicAddFrame);
        if(result==JFileChooser.APPROVE_OPTION){
            File file = jFileChooser.getSelectedFile();
            musicFilePath = file.getAbsolutePath();
        }
        return musicFilePath;
    }

    public static void saveMusic(String musicFilePath, String musicName, MusicAddFrame musicAddFrame) {
        String primaryName = "src/music/"+musicName+ musicFilePath.substring(musicFilePath.indexOf("."));
        int result = JOptionPane.showConfirmDialog(musicAddFrame,"确认添加？",null,JOptionPane.YES_NO_OPTION);
        if(result==JOptionPane.YES_OPTION){
            int row = MusicDao.saveMusic(musicName);
            if(row==1) {
                try{
                    FileInputStream fis = new FileInputStream(musicFilePath);
                    FileOutputStream fos = new FileOutputStream(primaryName);
                    byte[] buff = new byte[2048];
                    int len = 0;
                    while((len=fis.read(buff))>0) {
                        fos.write(buff,0,len);
                    }
                    fis.close();
                    fos.close();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(musicAddFrame,"添加成功");
            }else {
                JOptionPane.showMessageDialog(musicAddFrame,"添加失败");
            }
        }
    }

    public static void removeMusic(String musicName, MusicViewFrame musicViewFrame) {
        int result = JOptionPane.showConfirmDialog(musicViewFrame,
                "确认删除？",
                "系统提醒",
                JOptionPane.YES_NO_OPTION);
        if(result==JOptionPane.YES_OPTION){
            int row = MusicDao.removeMusic(musicName);
            if(row == 1) {
                String path = "src/music/"+musicName;
                File file = new File(path);
                if(file.exists()){
                    file.delete();
                    JOptionPane.showMessageDialog(musicViewFrame,
                            "删除成功！");
                }else{
                    JOptionPane.showMessageDialog(musicViewFrame,
                            "文件不存在！");
                }

            }else{
                JOptionPane.showMessageDialog(musicViewFrame,
                        "删除失败！");
            }
        }
    }

}
