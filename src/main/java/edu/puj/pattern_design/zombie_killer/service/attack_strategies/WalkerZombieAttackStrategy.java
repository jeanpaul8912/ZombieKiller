package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.zombies.WalkerZombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;

import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ANCHO_IMAGEN;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CAMINANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.GRUNIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;

public class WalkerZombieAttackStrategy extends AttackStrategy implements IAttackMovement {

    private WalkerZombie walkerZombie;

    @Override
    public void executeAttack(Enemy enemy) {
        walkerZombie = (WalkerZombie) enemy;
        attack(walkerZombie);
    }

    public void attack(WalkerZombie walkerZombie) {
        switch (walkerZombie.getCurrentStatus()) {
            case CAMINANDO:
                if (walkerZombie.getPosY() > POS_ATAQUE) {
                    walkerZombie.setCurrentStatus(ATACANDO);
                } else {
                    if (walkerZombie.getPosX() > ANCHO_PANTALLA - ANCHO_IMAGEN || walkerZombie.getPosX() < 0) {
                        moveInDirection(walkerZombie);
                    }

                    walkerZombie.posX = walkerZombie.getPosX() + walkerZombie.getDirectionX();
                    walkerZombie.setPosY(walkerZombie.getPosY() + walkerZombie.getDirectionY());

                    if (walkerZombie.getCurrentFrame() == 24) {
                        walkerZombie.setCurrentFrame((byte) 0);
                    } else {
                        walkerZombie.setCurrentFrame((byte) (walkerZombie.getCurrentFrame() + 1));
                    }
                }
                break;
            case ATACANDO:
                if (walkerZombie.getCurrentFrame() < 16) {
                    walkerZombie.setCurrentFrame((byte) (walkerZombie.getCurrentFrame() + 1));
                }
                break;
            case MURIENDO:
            case MURIENDO_INCENDIADO:
                if (walkerZombie.getCurrentFrame() < 17) {
                    walkerZombie.setCurrentFrame((byte) (walkerZombie.getCurrentFrame() + 1));
                }
                break;
            case GRUNIENDO:
                if (walkerZombie.getCurrentFrame() < 17) {
                    walkerZombie.setCurrentFrame((byte) (walkerZombie.getCurrentFrame() + 1));
                } else {
                    walkerZombie.setCurrentStatus(ATACANDO);
                }
                break;
        }
    }

    @Override
    public void moveInDirection(Enemy caminante) {
        WalkerZombie walkerZombieEnemy = (WalkerZombie) caminante;
        walkerZombieEnemy.setDirectionX((int) (Math.random() * 9) - 4);

        if (Math.abs(walkerZombieEnemy.getDirectionX()) < 4) {
            walkerZombieEnemy.setDirectionY(4 - Math.abs(walkerZombieEnemy.getDirectionX()));
        } else {
            walkerZombieEnemy.setDirectionY(2);
        }
    }

    public void finishAttack() {
        walkerZombie.setCurrentStatus(GRUNIENDO);
    }
}
