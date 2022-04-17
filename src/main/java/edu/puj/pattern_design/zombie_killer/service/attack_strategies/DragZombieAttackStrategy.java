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
        switch (dragZombie.getCurrentStatus()) {
            case CAMINANDO:
                if (dragZombie.getPosY() > POS_ATAQUE) {
                    dragZombie.setCurrentStatus(ATACANDO);
                } else {
                    dragZombie.setPosY(dragZombie.getPosY() + 3);
                    if (dragZombie.getCurrentFrame() == 31) {
                        dragZombie.setCurrentFrame((byte) 0);
                    } else {
                        dragZombie.setCurrentFrame((byte) (dragZombie.getCurrentFrame() + 1));
                    }
                }
                break;
            case ATACANDO:
                if (dragZombie.getCurrentFrame() < 16) {
                    dragZombie.setCurrentFrame((byte) (dragZombie.getCurrentFrame() + 1));
                }
                break;
            case MURIENDO:
            case MURIENDO_INCENDIADO:
                if (dragZombie.getCurrentFrame() < 11) {
                    dragZombie.setCurrentFrame((byte) (dragZombie.getCurrentFrame() + 1));
                }
                break;
        }
    }

    public void finishAttack() {
        dragZombie.setCurrentStatus(MURIENDO);
    }
}
