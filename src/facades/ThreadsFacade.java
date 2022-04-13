package facades;

import interfaz.InterfazZombieKiller;
import threads.HiloArma;
import threads.HiloBoss;
import threads.HiloEnemigo;
import threads.HiloGeneradorDeZombies;
import threads.HiloSonido;

public class ThreadsFacade {

    private HiloSonido zombieSoundThread;
    private final InterfazZombieKiller zombieInterface;

    public ThreadsFacade(InterfazZombieKiller zombieInterface) {
        this.zombieInterface = zombieInterface;
    }

    public void initializeEnemyThreads() {
        HiloGeneradorDeZombies zombieGeneratorThread = new HiloGeneradorDeZombies(zombieInterface, zombieInterface.getCampo());
        zombieGeneratorThread.start();
        HiloEnemigo enemyThread = new HiloEnemigo(zombieInterface, zombieInterface.getCampo().getZombNodoCercano(),
                zombieInterface.getCampo());
        enemyThread.start();
    }

    public void initializeGeneralSoundThread(String soundType) {
        HiloSonido generalSoundThread = new HiloSonido(soundType);
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
        HiloArma weaponThread;
        if (weaponType.equals("granada")) {
            weaponThread = new HiloArma(zombieInterface, zombieInterface.getGranada());
        } else if (weaponType.equals("cuchillo")) {
            weaponThread = new HiloArma(zombieInterface, zombieInterface.getCuchillo());
        } else {
            weaponThread = new HiloArma(zombieInterface, zombieInterface.getArmaActual());
        }
        
        weaponThread.start();
    }

    public void initializeBossThread() {
        HiloBoss boss = new HiloBoss(zombieInterface, zombieInterface.getBoss(), zombieInterface.getCampo());
        boss.start();
    }
}
