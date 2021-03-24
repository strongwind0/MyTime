package com.Venti.MyTime.dao;

import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.utils.DruidFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDao {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static int row = 0;

    //用户登录
    public static User login(String accountNumber, String password) {
        return LoginDao.loginUser(accountNumber,password);
    }

    public static int modifyUser(User user,String username,String path) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update User set Username=?,Path=? where UserAccount=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,path);
            preparedStatement.setString(3,user.getUserAccountNumber());
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int modifyPwd(User user,String newPwd) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update User set Password=? where UserAccount=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newPwd);
            preparedStatement.setString(2,user.getUserAccountNumber());
            row = preparedStatement.executeUpdate();
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
