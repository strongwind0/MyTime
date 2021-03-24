package com.Venti.MyTime.dao;

import com.Venti.MyTime.entity.Root;
import com.Venti.MyTime.entity.User;
import com.Venti.MyTime.utils.DruidFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static int row = 0;

    public static User loginUser(String name, String pwd) {
        User user = new User();
        try{
            connection = DruidFactory.getConnection();
            String sql = "select * from User where UserAccount= ? and Password=?" ;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,pwd);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String username = resultSet.getString("Username");
                String accountNumber = resultSet.getString("UserAccount");
                String password = resultSet.getString("Password");
                String path = resultSet.getString("Path");
                String createdTime = resultSet.getString("CreatedTime");
                user = new User(username,accountNumber,password,path,createdTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return user;
    }

    public static Root loginRoot(String name, String pwd){
        Root root = new Root();
        try{
            connection = DruidFactory.getConnection();
            String sql = "select * from root where RootAccount= ? and Password=?" ;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,pwd);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String rootName = resultSet.getString("RootName");
                String rootNumber = resultSet.getString("RootAccount");
                String password = resultSet.getString("Password");
                String path = resultSet.getString("Path");
                root = new Root(rootName,rootNumber,password,path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return root;
    }

    //关闭连接
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
