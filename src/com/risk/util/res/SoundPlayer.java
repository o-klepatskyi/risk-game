package com.risk.util.res;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public abstract class SoundPlayer {
    private static Clip backgroundMusic;
    private static Clip currentClip;

    private static boolean muted = true;

    public static Clip play(AudioInputStream audioInputStream) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            assert clip != null;
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        if(!muted)
            return clip;

        return null;
    }

    public static void gameOverSound() {
        currentClip = SoundPlayer.play(getInputStream("sounds/game_over.wav"));
        if(currentClip != null)
            currentClip.start();
    }

    public static void optionChosenSound() {
        currentClip = SoundPlayer.play(getInputStream("sounds/territory_chosen.wav"));
        if(currentClip != null)
            currentClip.start();
    }

    public static void buttonClickedSound() {
        currentClip = SoundPlayer.play(getInputStream("sounds/button_clicked.wav"));
        if(currentClip != null)
            currentClip.start();
    }

    public static void territoryChosenSound() {
        currentClip = SoundPlayer.play(getInputStream("sounds/territory_chosen.wav"));
        if(currentClip != null)
            currentClip.start();
    }

    public static void territoryClickedSound() {
        currentClip = SoundPlayer.play(getInputStream("sounds/territory_clicked.wav"));
        if(currentClip != null)
            currentClip.start();
    }

    public static void explosionSound() {
        currentClip = SoundPlayer.play(getInputStream("sounds/explosion.wav"));
        if(currentClip != null)
            currentClip.start();
    }

    public static void menuBackgroundMusic() {
        backgroundMusic = SoundPlayer.play(getInputStream("sounds/menu_music.wav"));
        if(backgroundMusic != null)
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
    }

    private static AudioInputStream getInputStream(String path) {
        try {
            return AudioSystem.getAudioInputStream(new BufferedInputStream(SoundPlayer.class.getResourceAsStream(path)));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean getMuted() {
        return muted;
    }

    public static void setMuted(boolean state) {
        muted = state;
        if(muted) {
            if(backgroundMusic != null)
                backgroundMusic.stop();

            if(currentClip != null)
                currentClip.stop();

            backgroundMusic = null;
            currentClip = null;
        }
        else {
            menuBackgroundMusic();
        }
    }
}
