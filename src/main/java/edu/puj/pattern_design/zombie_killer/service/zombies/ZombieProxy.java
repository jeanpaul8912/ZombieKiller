package edu.puj.pattern_design.zombie_killer.service.zombies;

import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CREEPING_ZOMBIE;

public class ZombieProxy implements IZombie {
	
	private Enemy enemigoCaminante;
	private Enemy enemigoRastero;
	private Zombie zombieCaminante;
	
	public ZombieProxy() {
		zombieCaminante = new WalkerZombie();
		enemigoCaminante = new WalkerZombie();
		enemigoRastero = new DragZombie((short)0,zombieCaminante);
	}
	
	@Override
	public Zombie inicialize(short newLevel, Zombie ZombieAtras, Zombie zombieAlFrente) {

		int zombieType = 0;
		Zombie zombie = null;

		if ((newLevel == 3 || newLevel == 4 || newLevel == 8)) {
			zombieType = (int) (Math.random() * 2);
		} else if (newLevel == 6 || newLevel == 9) {
			zombieType = CREEPING_ZOMBIE;
		}

		if (zombieType == CREEPING_ZOMBIE) {
			zombie = (DragZombie) enemigoRastero.cloneEnemy();
		} else {
			zombie = (WalkerZombie) enemigoCaminante.cloneEnemy();
		}
		
		zombie.inicialize(newLevel, ZombieAtras, zombieAlFrente);
		return zombie;
	}
}