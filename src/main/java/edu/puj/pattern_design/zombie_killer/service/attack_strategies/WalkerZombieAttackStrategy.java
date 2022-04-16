package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.zombies.WalkerZombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.ANCHO_PANTALLA;
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
        switch (walkerZombie.getEstadoActual()) {
            case CAMINANDO:
                if (walkerZombie.getPosY() > POS_ATAQUE) {
                    walkerZombie.setEstadoActual(ATACANDO);
                } else {
                    if (walkerZombie.getPosX() > ANCHO_PANTALLA - ANCHO_IMAGEN || walkerZombie.getPosX() < 0) {
                        moveInDirection(walkerZombie);
                    }

                    walkerZombie.posX = walkerZombie.getPosX() + walkerZombie.getDirectionX();
                    walkerZombie.setPosY(walkerZombie.getPosY() + walkerZombie.getDirectionY());

                    if (walkerZombie.getFrameActual() == 24) {
                        walkerZombie.setFrameActual((byte) 0);
                    } else {
                        walkerZombie.setFrameActual((byte) (walkerZombie.getFrameActual() + 1));
                    }
                }
                break;
            case ATACANDO:
                if (walkerZombie.getFrameActual() < 16) {
                    walkerZombie.setFrameActual((byte) (walkerZombie.getFrameActual() + 1));
                }
                break;
            case MURIENDO:
            case MURIENDO_INCENDIADO:
                if (walkerZombie.getFrameActual() < 17) {
                    walkerZombie.setFrameActual((byte) (walkerZombie.getFrameActual() + 1));
                }
                break;
            case GRUNIENDO:
                if (walkerZombie.getFrameActual() < 17) {
                    walkerZombie.setFrameActual((byte) (walkerZombie.getFrameActual() + 1));
                } else {
                    walkerZombie.setEstadoActual(ATACANDO);
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
        walkerZombie.setEstadoActual(GRUNIENDO);
    }
}
