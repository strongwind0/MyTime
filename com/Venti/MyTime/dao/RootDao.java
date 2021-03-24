package com.Venti.MyTime.dao;

import com.Venti.MyTime.entity.Root;
import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.utils.DruidFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RootDao {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static int row = 0;

    //登录
    public static Root login(String rootAccountNumber,String password) {
        return LoginDao.loginRoot(rootAccountNumber,password);
    }

    public static int modifyRootPwd(Root root, String newPassword) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update Root set Password=? where RootAccount=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newPassword);
            preparedStatement.setString(2,root.getRootAccountNumber());
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int modifyRoot(Root root,String rootName,String path) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update Root set RootName=?,Path=? where RootAccount=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,rootName);
            preparedStatement.setString(2,path);
            preparedStatement.setString(3,root.getRootAccountNumber());
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static List<User> findUser(String userAccount) {
        List<User> users = new ArrayList<User>();
        try {
            connection = DruidFactory.getConnection();
            String sql = "select * from user";
            String condition = "";
            if(userAccount!=null && !userAccount.equals("")) {
                condition +=" where UserAccount like '%"+userAccount+"%'";
            }
            sql +=condition;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String username = resultSet.getString("Username");
                String accountNumber = resultSet.getString("UserAccount");
                String password = resultSet.getString("Password");
                String path = resultSet.getString("Path");
                String createdTime = resultSet.getString("CreatedTime");
                User user = new User(username,accountNumber,password,path,createdTime);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return users;
    }

    public static int resetUserPwd(String userAccount) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update User set Password = '123456' where UserAccount=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userAccount);
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int removeUser(String userAccount) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "delete from user where UserAccount = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userAccount);
            row = preparedStatement.executeUpdate();
            sql = "drop table "+userAccount;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int createUser(User user) {
        try {
            connection = DruidFactory.getConnection();
            String sql = "insert " +
                    "into user(Username,UserAccount,Password,Path,CreatedTime) " +
                    "values(?,?,?,?,?); ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2, user.getUserAccountNumber());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getPath());
            preparedStatement.setString(5, user.getCreatedTime());
            row = preparedStatement.executeUpdate();
            sql = "create table "+user.getUserAccountNumber()+" (" +
                    "ID         varchar(255) NOT NULL," +
                    "StartTime  varchar(255) NULL,"+
                    "EndTime    varchar(255) NULL,"+
                    "Place      varchar(255) NULL,"+
                    "Thing      varchar(255) NULL,"+
                    "State      int NOT NULL DEFAULT 0,"+
                    "PRIMARY KEY (`ID`)" +
                    ");";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static void closeConnection() {
        try{
            if(null!=resultSet)
                resultSet.close();
            if(null!=preparedStatement)
                preparedStatement.close();
            if(null!=connection)
                connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
