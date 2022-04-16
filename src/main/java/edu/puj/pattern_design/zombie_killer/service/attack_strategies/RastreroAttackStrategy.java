package edu.puj.pattern_design.zombie_killer.service.attack_strategies;

import edu.puj.pattern_design.zombie_killer.service.zombies.Enemy;
import edu.puj.pattern_design.zombie_killer.service.zombies.Rastrero;

import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CAMINANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;

public class RastreroAttackStrategy extends AttackStrategy {

    private Rastrero rastrero;

    @Override
    public void executeAttack(Enemy enemy) {
        rastrero = (Rastrero) enemy;
        attack(rastrero);
    }

    public void attack(Rastrero rastrero) {
        switch (rastrero.getEstadoActual()) {
            case CAMINANDO:
                if (rastrero.getPosY() > POS_ATAQUE) {
                    rastrero.setEstadoActual(ATACANDO);
                } else {
                    rastrero.setPosY(rastrero.getPosY() + 3);
                    if (rastrero.getFrameActual() == 31) {
                        rastrero.setFrameActual((byte) 0);
                    } else {
                        rastrero.setFrameActual((byte) (rastrero.getFrameActual() + 1));
                    }
                }
                break;
            case ATACANDO:
                if (rastrero.getFrameActual() < 16) {
                    rastrero.setFrameActual((byte) (rastrero.getFrameActual() + 1));
                }
                break;
            case MURIENDO:
            case MURIENDO_INCENDIADO:
                if (rastrero.getFrameActual() < 11) {
                    rastrero.setFrameActual((byte) (rastrero.getFrameActual() + 1));
                }
                break;
        }
    }

    public void terminaDeAtacar() {
        rastrero.setEstadoActual(MURIENDO);
    }
}
