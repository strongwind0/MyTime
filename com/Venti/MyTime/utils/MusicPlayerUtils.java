package com.Venti.MyTime.utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MusicPlayerUtils implements Runnable {

    private String musicPath;
    private Player player;

    public MusicPlayerUtils(String musicPath) {
        this.musicPath = musicPath;
    }

    @Override
    public void run() {
        try{
            BufferedInputStream stream = new BufferedInputStream(
                    new FileInputStream(musicPath));
            player = new Player(stream);
            player.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
