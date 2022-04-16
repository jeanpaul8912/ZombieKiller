package edu.puj.pattern_design.zombie_killer.gui.facade;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.thread.WeaponThread;
import edu.puj.pattern_design.zombie_killer.gui.thread.WeaponBoss;
import edu.puj.pattern_design.zombie_killer.gui.thread.EnemyThread;
import edu.puj.pattern_design.zombie_killer.gui.thread.ZombieGeneratorThread;
import edu.puj.pattern_design.zombie_killer.gui.thread.SoundThread;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_GRENADE_TYPE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_KNIFE_TYPE;

public class ThreadsFacade {

    private SoundThread zombieSoundThread;
    private final ZombieKillerGUI zombieInterface;

    public ThreadsFacade(ZombieKillerGUI zombieInterface) {
        this.zombieInterface = zombieInterface;
    }

    public void initializeEnemyThreads() {
        ZombieGeneratorThread zombieGeneratorThread = new ZombieGeneratorThread(zombieInterface, zombieInterface.getCamp());
        zombieGeneratorThread.start();
        EnemyThread enemyThread = new EnemyThread(zombieInterface, zombieInterface.getCamp().getZombieNearNode(),
                zombieInterface.getCamp());
        enemyThread.start();
    }

    public void initializeGeneralSoundThread(String soundType) {
        SoundThread generalSoundThread = new SoundThread(soundType);
        generalSoundThread.start();
    }

    public void initializeZombieSoundThread(String soundType) {
        zombieSoundThread = new SoundThread(soundType);
        zombieSoundThread.start();
    }

    public void soundStop() {
        if (zombieSoundThread != null) {
            zombieSoundThread.detenerSonido();
        }
    }

    public void initializeWeaponsThread(String weaponType) {
        WeaponThread weaponThread;
        if (weaponType.equals(WEAPON_GRENADE_TYPE)) {
            weaponThread = new WeaponThread(zombieInterface, zombieInterface.getGrenade());
        } else if (weaponType.equals(WEAPON_KNIFE_TYPE)) {
            weaponThread = new WeaponThread(zombieInterface, zombieInterface.getKnife());
        } else {
            weaponThread = new WeaponThread(zombieInterface, zombieInterface.getCurrentWeapon());
        }

        weaponThread.start();
    }

    public void initializeBossThread() {
        WeaponBoss boss = new WeaponBoss(zombieInterface, zombieInterface.getBoss(), zombieInterface.getCamp());
        boss.start();
    }
}
