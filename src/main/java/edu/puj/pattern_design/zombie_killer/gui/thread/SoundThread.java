package edu.puj.pattern_design.zombie_killer.gui.thread;

import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;

@Slf4j
public class SoundThread extends Thread {

    private final String ruta;
    private Clip sonido;

    public SoundThread(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public void run() {
        try (BufferedInputStream bufInS = new BufferedInputStream(
                getClass().getResourceAsStream("/sounds/" + ruta + ".wav"))) {
            AudioInputStream audInS = AudioSystem.getAudioInputStream(bufInS);
            sonido = AudioSystem.getClip();
            sonido.open(audInS);
            sonido.start();
            sleep(3000);

            if (ruta.equals("zombies")) {
                while (sonido.isRunning()) {
                    sleep(500);
                }

                if (sonido.isOpen()) {
                    run();
                }
            } else if (ruta.equals("sirena")) {
                while (sonido.isRunning()) {
                    sleep(500);
                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    public void detenerSonido() {
        if (sonido != null)
            sonido.close();
    }
}
