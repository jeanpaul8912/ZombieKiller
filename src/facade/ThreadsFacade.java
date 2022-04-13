package facade;

import hilo.HiloArma;
import hilo.HiloBoss;
import hilo.HiloEnemigo;
import hilo.HiloGeneradorDeZombies;
import hilo.HiloSonido;
import interfaz.InterfazZombieKiller;

public class ThreadsFacade {

	private HiloArma weaponThread;
	private HiloEnemigo EnemyThread;
	private HiloSonido zombieSoundThread;
	private HiloSonido generalSoundThread;
	private HiloBoss boss;
	private HiloGeneradorDeZombies zombieGeneratorThread;
	private InterfazZombieKiller zombieInterface;

	public ThreadsFacade(InterfazZombieKiller zombieInterface) {
		this.zombieInterface = zombieInterface;
	}

	public void initializeEnemyThreads() {
		zombieGeneratorThread = new HiloGeneradorDeZombies(zombieInterface, zombieInterface.getCampo());
		zombieGeneratorThread.start();
		EnemyThread = new HiloEnemigo(zombieInterface, zombieInterface.getCampo().getZombNodoCercano(),
				zombieInterface.getCampo());
		EnemyThread.start();
	}

	public void initializeGeneralSoundThread(String soundType) {
		generalSoundThread = new HiloSonido(soundType);
		generalSoundThread.start();
	}

	public void initializeZombieSoundThread(String soundType) {
		zombieSoundThread = new HiloSonido(soundType);
		zombieSoundThread.start();
	}

	public void soundStop() {
		if (zombieSoundThread != null) {
			zombieSoundThread.detenerSonido();
		}
	}

	public void initializeWeaponsThread(String weaponType) {
		if (weaponType == "granada") {
			weaponThread = new HiloArma(zombieInterface, zombieInterface.getGranada());
		} else if (weaponType == "cuchillo") {
			weaponThread = new HiloArma(zombieInterface, zombieInterface.getCuchillo());
		} else {
			weaponThread = new HiloArma(zombieInterface, zombieInterface.getArmaActual());
		}
		weaponThread.start();
	}

	public void initializeBossThread() {
		boss = new HiloBoss(zombieInterface, zombieInterface.getBoss(), zombieInterface.getCampo());
		boss.start();
	}
}
