package com.Venti.MyTime.service;

import com.Venti.MyTime.dao.AffairDao;
import com.Venti.MyTime.dao.UserDao;
import com.Venti.MyTime.entity.Affair;
import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.utils.CommonUtils;
import com.Venti.MyTime.view.user.AffairsViewFrame;
import com.Venti.MyTime.view.user.UserViewFrame;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserService {

    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String timeFormat="\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d \\d\\d\\:\\d\\d\\:\\d\\d";

    public static String chooserAvatar(UserViewFrame userViewFrame) {
        String imageFilePath ="";
        File file = null;
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
        int result = jFileChooser.showOpenDialog(userViewFrame);
        if(result==JFileChooser.APPROVE_OPTION){
            file = jFileChooser.getSelectedFile();
            imageFilePath = file.getAbsolutePath();
        }
        return imageFilePath;
    }

    public static String saveUser(User user,String name,String imageFilePath,UserViewFrame userViewFrame) {
        String relativePath = imageFilePath.substring(imageFilePath.lastIndexOf("\\"));
        relativePath = "/image/"+
                CommonUtils.getFileName()+
                relativePath.substring(relativePath.indexOf("."));
        int result = JOptionPane.showConfirmDialog(userViewFrame,
                "是否保存",
                "系统提醒",
                JOptionPane.YES_NO_OPTION);
        if(result==0) {
            if(UserDao.modifyUser(user,name,relativePath)==1){
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
                JOptionPane.showMessageDialog(userViewFrame,"保存成功");
                user.setUsername(name);
                user.setPath(relativePath);

            }else{
                JOptionPane.showMessageDialog(userViewFrame, "保存失败，请稍后重试");
            }
        }
        return relativePath;
    }

    public static void remove(String userAccount, String ID, AffairsViewFrame affairsViewFrame) {
        int result = JOptionPane.showConfirmDialog(affairsViewFrame,
                "确认删除？",
                "系统提醒",
                JOptionPane.YES_NO_OPTION);
        if(result==JOptionPane.YES_OPTION){
            int row = AffairDao.removeAffair(userAccount,ID);
            if(row == 1){
                JOptionPane.showMessageDialog(affairsViewFrame,
                        "删除成功！");
            }else{
                JOptionPane.showMessageDialog(affairsViewFrame,
                        "删除失败！");
            }
        }
    }

    public static void finish(String userAccount, String ID, AffairsViewFrame affairsViewFrame) {
        int result = JOptionPane.showConfirmDialog(affairsViewFrame,
                "确认完成？",
                "系统提醒",
                JOptionPane.YES_NO_OPTION);
        if(result==JOptionPane.YES_OPTION) {
            int row = AffairDao.finishAffair(userAccount,ID);
            if(row == 1) {
                JOptionPane.showMessageDialog(affairsViewFrame,
                        "任务已完成！");
            }else {
                JOptionPane.showMessageDialog(affairsViewFrame,
                        "任务完成点击失败，请稍后重试");
            }
        }
    }

    public static int timeout(String userAccount) {
        String nowDate = fmt.format(new Date());
        List<Affair> affairList = new ArrayList<Affair>();
        affairList = AffairDao.findAffairs(userAccount,"");
        int total = 0;
        try {
            for(Affair affair : affairList) {
                if(fmt.parse(affair.getEndTime()).compareTo(fmt.parse(nowDate))<0) {
                    AffairDao.affairTimeout(userAccount,affair.getID());
                    total++;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static int savaAffair(JInternalFrame jInternalFrame,String userAccount,Affair affair,int type) {
        if(affair.getStartTime() != null && !affair.getStartTime().equals("")) {
            if(affair.getEndTime() != null && !affair.getEndTime().equals("")) {
                if(affair.getPlace() != null && !affair.getPlace().equals("")) {
                    if(affair.getThing() != null && !affair.getThing().equals("")) {
                        if(affair.getStartTime().matches(timeFormat) &&
                                affair.getEndTime().matches(timeFormat)) {
                            boolean existed = false;
                            try{
                                for (Affair affairs : AffairDao.findAffairs(userAccount,"")) {
                                    if(type==1) {
                                        if(affair.getID().equals(affairs.getID())) {
                                            if(fmt.parse(affairs.getStartTime()).
                                                    compareTo(fmt.parse(affair.getStartTime()))<=0 &&
                                                    fmt.parse(affair.getEndTime())
                                                            .compareTo(fmt.parse(affairs.getEndTime()))<=0) {
                                                existed = false;
                                                continue;
                                            }
                                        }
                                    }
                                    if((fmt.parse(affair.getStartTime()).
                                            compareTo(fmt.parse(
                                                    affairs.getEndTime()))<=0 &&
                                            fmt.parse(affairs.getStartTime()).
                                                    compareTo(fmt.parse(
                                                            affair.getStartTime()))<=0) ||
                                            (fmt.parse(affair.getEndTime()).
                                                    compareTo(fmt.parse(
                                                            affairs.getEndTime()))<=0 &&
                                                    fmt.parse(affairs.getStartTime()).
                                                            compareTo(fmt.parse(
                                                                    affair.getEndTime()))<=0) ||
                                            (fmt.parse(affair.getStartTime()).
                                                    compareTo(fmt.parse(
                                                            affairs.getStartTime()))<=0 &&
                                                    fmt.parse(affairs.getEndTime()).
                                                            compareTo(fmt.parse(
                                                                    affair.getEndTime()))<=0)) {
                                        existed = true;
                                        break;
                                    }
                                }
                                if(fmt.parse(affair.getEndTime()).
                                        compareTo(fmt.parse(affair.getStartTime()))<=0) {
                                    //结束时间不能早于开始时间
                                    JOptionPane.showMessageDialog(jInternalFrame,
                                            "结束时间不得早于开始时间");
                                    return 0;
                                }else if(fmt.parse(affair.getStartTime()).
                                        compareTo(fmt.parse(fmt.format(new Date())))<0){
                                    //开始时间不能早于现在
                                    JOptionPane.showMessageDialog(jInternalFrame,
                                            "开始时间不得早于现在");
                                    return 0;
                                }else if(existed) {
                                    JOptionPane.showMessageDialog(jInternalFrame,
                                            "该时段已存在事务，请更换时段");
                                    return 0;
                                }else{
                                    if(type==0) {
                                        int row = AffairDao.addAffair(userAccount,
                                                affair);
                                        if(row == 1) {
                                            JOptionPane.showMessageDialog(jInternalFrame,
                                                    "添加成功");
                                            return 1;
                                        }else {
                                            JOptionPane.showMessageDialog(jInternalFrame,
                                                    "添加失败,请检查输入");
                                            return -6;
                                        }
                                    }else if(type==1) {
                                        int row = AffairDao.modifyAffair(userAccount,affair);
                                        if(row == 1) {
                                            JOptionPane.showMessageDialog(jInternalFrame,
                                                    "修改成功");
                                            return 1;
                                        }else {
                                            JOptionPane.showMessageDialog(jInternalFrame,
                                                    "修改失败,请检查输入");
                                            return -6;
                                        }
                                    }
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else {
                            return -5;
                        }
                    }else{
                        return -4;
                    }
                }else{
                    return -3;
                }
            }else{
                return -2;
            }
        }else{
            return -1;
        }
        return 0;
    }
}
