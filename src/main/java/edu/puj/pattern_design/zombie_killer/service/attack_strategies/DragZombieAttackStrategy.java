package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;
import edu.puj.pattern_design.zombie_killer.service.zombies.DragZombie;

import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CAMINANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;

public class DragZombieAttackStrategy extends AttackStrategy {

    private DragZombie dragZombie;

    @Override
    public void executeAttack(Enemy enemy) {
        dragZombie = (DragZombie) enemy;
        attack(dragZombie);
    }

    public void attack(DragZombie dragZombie) {
        switch (dragZombie.getEstadoActual()) {
            case CAMINANDO:
                if (dragZombie.getPosY() > POS_ATAQUE) {
                    dragZombie.setEstadoActual(ATACANDO);
                } else {
                    dragZombie.setPosY(dragZombie.getPosY() + 3);
                    if (dragZombie.getFrameActual() == 31) {
                        dragZombie.setFrameActual((byte) 0);
                    } else {
                        dragZombie.setFrameActual((byte) (dragZombie.getFrameActual() + 1));
                    }
                }
                break;
            case ATACANDO:
                if (dragZombie.getFrameActual() < 16) {
                    dragZombie.setFrameActual((byte) (dragZombie.getFrameActual() + 1));
                }
                break;
            case MURIENDO:
            case MURIENDO_INCENDIADO:
                if (dragZombie.getFrameActual() < 11) {
                    dragZombie.setFrameActual((byte) (dragZombie.getFrameActual() + 1));
                }
                break;
        }
    }

    public void finishAttack() {
        dragZombie.setEstadoActual(MURIENDO);
    }
}
