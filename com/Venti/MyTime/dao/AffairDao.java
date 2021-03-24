package com.Venti.MyTime.dao;

import com.Venti.MyTime.entity.Affair;
import com.Venti.MyTime.utils.DruidFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AffairDao {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static int row = 0;

    //事务ID的格式
    private static final SimpleDateFormat fmt = new SimpleDateFormat("yyMMddHHmmssssss");

    public static int addAffair(String userAccount, Affair affair) {
        try{
            //Affair affair = new Affair(fmt.format(new Date()),start,end,place,thing,0);
            affair.setID(fmt.format(new Date()));
            affair.setState(0);
            connection = DruidFactory.getConnection();
            String sql = "insert " +
                    "into " +userAccount
                    +"(ID,StartTime,EndTime,Place,Thing,State) values(?,?,?,?,?,0)" ;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, affair.getID());
            preparedStatement.setString(2, affair.getStartTime());
            preparedStatement.setString(3, affair.getEndTime());
            preparedStatement.setString(4, affair.getPlace());
            preparedStatement.setString(5, affair.getThing());
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int removeAffair(String userAccount,String ID) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "delete from "+userAccount+" where ID=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int modifyAffair(String userAccount, Affair affair) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update "+userAccount+" set StartTime=?,EndTime=?,Place=?,Thing=? where ID=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, affair.getStartTime());
            preparedStatement.setString(2, affair.getEndTime());
            preparedStatement.setString(3, affair.getPlace());
            preparedStatement.setString(4, affair.getThing());
            preparedStatement.setString(5, affair.getID());
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int finishAffair(String userAccount, String ID) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update "+userAccount+" set State=1 where ID=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static List<Affair> findAffairs(String userAccount, String id) {
        List<Affair> affairs = new ArrayList<Affair>();
        try {
            connection = DruidFactory.getConnection();
            String sql = "select * from "+ userAccount +" where State != 1";
            String condition = "";
            if(id!=null && !id.equals("")) {
                condition += " and ID like '%"+id+"%'";
            }
            sql += condition;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String ID = resultSet.getString("ID");
                String startTime = resultSet.getString("StartTime");
                String endTime = resultSet.getString("EndTime");
                String place = resultSet.getString("Place");
                String thing = resultSet.getString("Thing");
                int state = resultSet.getInt("State");
                Affair affair = new Affair(ID,startTime,endTime,place,thing,state);
                affairs.add(affair);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return affairs;
    }

    public static Affair preciseSearchAffair(String userAccount, String ID) {
        Affair affair = null;
        try{
            connection = DruidFactory.getConnection();
            String sql = "select * from "+userAccount+" where ID=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                //String ID = resultSet.getString("ID");
                String startTime = resultSet.getString("StartTime");
                String endTime = resultSet.getString("EndTime");
                String place = resultSet.getString("Place");
                String thing = resultSet.getString("Thing");
                int state = resultSet.getInt("State");
                affair = new Affair(ID,startTime,endTime,place,thing,state);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return affair;
    }

    public static int affairTimeout(String userAccount,String ID) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "update "+userAccount+" set state = -1 where ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);
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
