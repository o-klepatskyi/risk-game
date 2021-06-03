package util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private static Clip backgroundMusic;
    private static Clip clip;

    public static void play(String soundName, Clip clip) {
        pause(clip);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
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
        SoundPlayer.play("res/sounds/territory_chosen.wav", clip);
    }

    public static void buttonClickedSound() {
        SoundPlayer.play("res/sounds/button_clicked.wav", clip);
    }

    public static void territoryChosenSound() {
        SoundPlayer.play("res/sounds/territory_chosen.wav", clip);
    }

    public static void territoryClickedSound() {
        SoundPlayer.play("res/sounds/territory_clicked.wav", clip);
    }

    public static void explosionSound() {
        SoundPlayer.play("res/sounds/explosion.wav", clip);
    }

    public static void menuBackgroundMusic() {
        SoundPlayer.play("res/sounds/menu_music.wav", backgroundMusic);
    }
}
