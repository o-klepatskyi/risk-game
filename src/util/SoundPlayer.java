package util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SoundPlayer {
    private static Clip backgroundMusic;
    private static Clip clip;

    public static void play(AudioInputStream audioInputStream, Clip clip) {
        pause(clip);
        clip = null;
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
        clip.start();
    }

    public static void pause(Clip clip) {
        if(clip != null)
            clip.stop();
    }

    public static void optionChosenSound() {
        SoundPlayer.play(getInputStream("res/sounds/territory_chosen.wav"), clip);
    }

    public static void buttonClickedSound() {
        SoundPlayer.play(getInputStream("res/sounds/button_clicked.wav"), clip);
    }

    public static void territoryChosenSound() {
        SoundPlayer.play(getInputStream("res/sounds/territory_chosen.wav"), clip);
    }

    public static void territoryClickedSound() {
        SoundPlayer.play(getInputStream("res/sounds/territory_clicked.wav"), clip);
    }

    public static void explosionSound() {
        SoundPlayer.play(getInputStream("res/sounds/explosion.wav"), clip);
    }

    public static void menuBackgroundMusic() {
        SoundPlayer.play(getInputStream("res/sounds/menu_music.wav"), backgroundMusic);
    }

    private static AudioInputStream getInputStream(String path) {
        try {
            return AudioSystem.getAudioInputStream(new BufferedInputStream(SoundPlayer.class.getResourceAsStream(path)));
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
