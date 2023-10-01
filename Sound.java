import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private Clip clip;

    public Sound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }

    public void playWithFadeIn(int fadeInDurationInMillis) {
        if (clip != null) {
            Thread fadeInThread = new Thread(() -> {
                float initialVolume = 0.0f;
                float targetVolume = 1.0f;
                float volumeDelta = (targetVolume - initialVolume) / (fadeInDurationInMillis / 100f);
                float dB = (float)(Math.log(initialVolume)/Math.log(10.0)*20.0);
                
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(dB);
                float newVolume = initialVolume;
                
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                while (newVolume < targetVolume) {
                    try {
                        Thread.sleep(100); // Adjust the sleep duration for smoother fading
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    newVolume += volumeDelta;
                    dB = (float)(Math.log(newVolume)/Math.log(10.0)*20.0);
                    gainControl.setValue(dB);
                }
            });

            fadeInThread.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
