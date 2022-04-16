package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;
import org.apache.commons.lang3.math.NumberUtils;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;

public abstract class AttackStrategy {

    public abstract void executeAttack(Enemy enemy);

    public abstract void terminaDeAtacar();

    public void enemigoTerminaSuGolpe(SurvivorCamp campo) {
        campo.getPersonaje().setBlooded(false);
        terminaDeAtacar();
    }

    public void enemigoAtaca(SurvivorCamp campo) {
        campo.getPersonaje().setBlooded(true);
        campo.getPersonaje().setHealth((byte) (campo.getPersonaje().getHealth() - 1));
        if (campo.getPersonaje().getHealth() <= NumberUtils.INTEGER_ZERO) {
            campo.setEstadoJuego(SIN_PARTIDA);
        }
    }
}