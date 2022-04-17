package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ANCHO_IMAGEN;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_INICIAL;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.VOLANDO;

public class BossZombieAttackStrategy extends AttackStrategy implements IAttackMovement {

    private Boss boss;

    @Override
    public void executeAttack(Enemy enemy) {
        boss = (Boss) enemy;
        attack(boss);
    }

    public void attack(Boss boss) {
        if (boss.getCurrentStatus().equals(VOLANDO)) {
            if (boss.getPosY() > POS_ATAQUE) {
                boss.setCurrentStatus(ATACANDO);
            } else {
                if (boss.getPosX() > ANCHO_PANTALLA - ANCHO_IMAGEN || boss.getPosX() < 0) {
                    moveInDirection(boss);
                }
                boss.setPosX(boss.getPosX() + boss.getDirectionX());
                boss.setPosY(boss.getPosY() + boss.getDirectionY());

                if (boss.getCurrentFrame() < 13) {
                    boss.setCurrentFrame((byte) (boss.getCurrentFrame() + 1));
                } else {
                    boss.setCurrentFrame((byte) 0);
                }
            }
        } else if (boss.getCurrentStatus().equals(ATACANDO)) {
            if (boss.getCurrentFrame() < 21) {
                boss.setCurrentFrame((byte) (boss.getCurrentFrame() + 1));
            }
        }
    }

    @Override
    public void moveInDirection(Enemy enemy) {
        Boss bossEnemy = (Boss) enemy;
        bossEnemy.setDirectionX((int) (Math.random() * 13) - 6);

        if (bossEnemy.getDirectionX() > 0 && bossEnemy.getDirectionX() < 6) {
            bossEnemy.setDirectionY(6 - bossEnemy.getDirectionX());
        } else if (bossEnemy.getDirectionX() <= 0 && bossEnemy.getDirectionX() > -6) {
            bossEnemy.setDirectionY(6 + bossEnemy.getDirectionX());
        } else {
            bossEnemy.setDirectionY(2);
        }
    }

    public void finishAttack() {
        boss.setCurrentStatus(VOLANDO);
        boss.setPosY(POS_INICIAL);
        moveInDirection(boss);
        boss.setPosX(boss.aleatoryPositionX());
    }
}