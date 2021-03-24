package com.Venti.MyTime.entity;

//音乐实体类
public class Music {

    private int ID;
    private String musicName;

    public Music(int ID, String musicName) {
        this.ID = ID;
        this.musicName = musicName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    @Override
    public String toString() {
        return "MusicDao{" +
                "ID=" + ID +
                ", musicName='" + musicName + '\'' +
                '}';
    }
}
