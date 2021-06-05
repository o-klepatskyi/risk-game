package util.res;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

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
        SoundPlayer.play(getInputStream("sounds/territory_chosen.wav"), clip);
    }

    public static void buttonClickedSound() {
        SoundPlayer.play(getInputStream("sounds/button_clicked.wav"), clip);
    }

    public static void territoryChosenSound() {
        SoundPlayer.play(getInputStream("sounds/territory_chosen.wav"), clip);
    }

    public static void territoryClickedSound() {
        SoundPlayer.play(getInputStream("sounds/territory_clicked.wav"), clip);
    }

    public static void explosionSound() {
        SoundPlayer.play(getInputStream("sounds/explosion.wav"), clip);
    }

    public static void menuBackgroundMusic() {
        SoundPlayer.play(getInputStream("sounds/menu_music.wav"), backgroundMusic);
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
