package mundo.attackStrategies;

import mundo.zombies.Enemigo;
import mundo.zombies.Rastrero;

public class RastreroAttackStrategy extends AttackStrategy {

    private Rastrero rastrero;

    @Override
    public void executeAttack(Enemigo enemy) {
        rastrero = (Rastrero) enemy;
        attack(rastrero);
    }

    public void attack(Rastrero rastrero) {
        switch (rastrero.getEstadoActual()) {
            case Rastrero.CAMINANDO:
                if (rastrero.getPosY() > Rastrero.POS_ATAQUE) {
                    rastrero.setEstadoActual(Rastrero.ATACANDO);
                } else {
                    rastrero.setPosY(rastrero.getPosY() + 3);
                    if (rastrero.getFrameActual() == 31) {
                        rastrero.setFrameActual((byte) 0);
                    } else {
                        rastrero.setFrameActual((byte) (rastrero.getFrameActual() + 1));
                    }
                }
                break;
            case Rastrero.ATACANDO:
                if (rastrero.getFrameActual() < 16) {
                    rastrero.setFrameActual((byte) (rastrero.getFrameActual() + 1));
                }
                break;
            case Rastrero.MURIENDO:
            case Rastrero.MURIENDO_INCENDIADO:
                if (rastrero.getFrameActual() < 11) {
                    rastrero.setFrameActual((byte) (rastrero.getFrameActual() + 1));
                }
                break;
        }
    }

    public void terminaDeAtacar() {
        rastrero.setEstadoActual(Rastrero.MURIENDO);
    }
}
