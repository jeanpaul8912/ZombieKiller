package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;
import org.apache.commons.lang3.math.NumberUtils;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;

public abstract class AttackStrategy {

    public abstract void executeAttack(Enemy enemy);

    public abstract void finishAttack();

    public void enemyFinishAttack(SurvivorCamp campo) {
        campo.getCharacter().setBlooded(false);
        finishAttack();
    }

    public void enemyAttacks(SurvivorCamp campo) {
        campo.getCharacter().setBlooded(true);
        campo.getCharacter().setHealth((byte) (campo.getCharacter().getHealth() - 1));

        if (campo.getCharacter().getHealth() <= NumberUtils.INTEGER_ZERO) {
            campo.setGameStatus(SIN_PARTIDA);
        }
    }
}