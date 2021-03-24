package com.Venti.MyTime.dao;

import com.Venti.MyTime.entity.Music;
import com.Venti.MyTime.utils.DruidFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MusicDao {

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static int row = 0;

    //获取音乐列表
    public static List<String> getMusicList() {
        List<String> musicList = new ArrayList<String>();
        try{
            connection = DruidFactory.getConnection();
            String sql = "select Music from Music";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                musicList.add(resultSet.getString("Music"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return musicList;
    }

    public static List<Music> findMusic(String musicName) {
        List<Music> musics = new ArrayList<Music>();
        try{
            connection = DruidFactory.getConnection();
            String sql = "select * from Music where Music like '%"+musicName+"%'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Music");
                Music music = new Music(id,name);
                musics.add(music);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return musics;
    }

    public static int saveMusic(String musicName) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "insert into Music(Music) values(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,musicName+".mp3");
            row = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return row;
    }

    public static int removeMusic(String musicName) {
        try{
            connection = DruidFactory.getConnection();
            String sql = "delete from music where Music = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,musicName);
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
