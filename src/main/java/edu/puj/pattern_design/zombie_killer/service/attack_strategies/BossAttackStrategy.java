package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ANCHO_IMAGEN;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_INICIAL;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.VOLANDO;

public class BossAttackStrategy extends AttackStrategy implements IAttackMovement {

    private Boss boss;

    @Override
    public void executeAttack(Enemy enemy) {
        boss = (Boss) enemy;
        attack(boss);
    }

    public void attack(Boss boss) {
        if (boss.getEstadoActual().equals(VOLANDO)) {
            if (boss.getPosY() > POS_ATAQUE) {
                boss.setEstadoActual(ATACANDO);
            } else {
                if (boss.getPosX() > ANCHO_PANTALLA - ANCHO_IMAGEN || boss.getPosX() < 0) {
                    moverEnDireccion(boss);
                }
                boss.setPosX(boss.getPosX() + boss.getDireccionX());
                boss.setPosY(boss.getPosY() + boss.getDireccionY());

                if (boss.getFrameActual() < 13) {
                    boss.setFrameActual((byte) (boss.getFrameActual() + 1));
                } else {
                    boss.setFrameActual((byte) 0);
                }
            }
        } else if (boss.getEstadoActual().equals(ATACANDO)) {
            if (boss.getFrameActual() < 21) {
                boss.setFrameActual((byte) (boss.getFrameActual() + 1));
            }
        }
    }

    @Override
    public void moverEnDireccion(Enemy enemy) {
        Boss bossEnemy = (Boss) enemy;
        bossEnemy.setDireccionX((int) (Math.random() * 13) - 6);

        if (bossEnemy.getDireccionX() > 0 && bossEnemy.getDireccionX() < 6) {
            bossEnemy.setDireccionY(6 - bossEnemy.getDireccionX());
        } else if (bossEnemy.getDireccionX() <= 0 && bossEnemy.getDireccionX() > -6) {
            bossEnemy.setDireccionY(6 + bossEnemy.getDireccionX());
        } else {
            bossEnemy.setDireccionY(2);
        }
    }

    public void terminaDeAtacar() {
        boss.setEstadoActual(VOLANDO);
        boss.setPosY(POS_INICIAL);
        moverEnDireccion(boss);
        boss.setPosX(boss.posAleatoriaX());
    }
}