package mundo.attackStrategies;

import mundo.zombies.Enemigo;
import mundo.zombies.Rastrero;

import static mundo.constants.ZombiesConstants.ATACANDO;
import static mundo.constants.ZombiesConstants.CAMINANDO;
import static mundo.constants.ZombiesConstants.MURIENDO;
import static mundo.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static mundo.constants.ZombiesConstants.POS_ATAQUE;

public class RastreroAttackStrategy extends AttackStrategy {

    private Rastrero rastrero;

    @Override
    public void executeAttack(Enemigo enemy) {
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
