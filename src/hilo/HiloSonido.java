package hilo;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import interfaz.InterfazZombieKiller;

public class HiloSonido extends Thread {

	private String ruta;
	private Clip sonido;

	public HiloSonido(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public void run() {
		BufferedInputStream bufInS = new BufferedInputStream(
				getClass().getResourceAsStream("/sonidos/" + ruta + ".wav"));
		try {
			AudioInputStream audInS = AudioSystem.getAudioInputStream(bufInS);
			sonido = AudioSystem.getClip();
			sonido.open(audInS);
			sonido.start();
			sleep(3000);
			if (ruta.equals("zombies")) {
				while (sonido.isRunning())
					sleep(500);
				if (sonido.isOpen())
					run();
			} else if (ruta.equals("sirena"))
				while (sonido.isRunning())
					sleep(500);
			sonido.close();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void detenerSonido() {
		if (sonido!= null)
		sonido.close();
	}
}
