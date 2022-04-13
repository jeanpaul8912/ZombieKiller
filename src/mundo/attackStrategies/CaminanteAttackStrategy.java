package mundo.attackStrategies;

import mundo.camp.SurvivorCamp;
import mundo.zombies.Caminante;
import mundo.zombies.Enemigo;

public class CaminanteAttackStrategy extends AttackStrategy implements IAttackMovement {

    private Caminante caminante;

    @Override
    public void executeAttack(Enemigo enemy) {
        caminante = (Caminante) enemy;
        attack(caminante);
    }

    public void attack(Caminante caminante) {
        switch (caminante.getEstadoActual()) {
            case Caminante.CAMINANDO:
                if (caminante.getPosY() > Caminante.POS_ATAQUE) {
                    caminante.setEstadoActual(Caminante.ATACANDO);
                } else {
                    if (caminante.getPosX() > SurvivorCamp.ANCHO_PANTALLA - Caminante.ANCHO_IMAGEN || caminante.getPosX() < 0) {
                        moverEnDireccion(caminante);
                    }

                    caminante.posX = caminante.getPosX() + caminante.getDireccionX();
                    caminante.setPosY(caminante.getPosY() + caminante.getDireccionY());

                    if (caminante.getFrameActual() == 24) {
                        caminante.setFrameActual((byte) 0);
                    } else {
                        caminante.setFrameActual((byte) (caminante.getFrameActual() + 1));
                    }
                }
                break;
            case Caminante.ATACANDO:
                if (caminante.getFrameActual() < 16) {
                    caminante.setFrameActual((byte) (caminante.getFrameActual() + 1));
                }
                break;
            case Caminante.MURIENDO:
            case Caminante.MURIENDO_INCENDIADO:
                if (caminante.getFrameActual() < 17) {
                    caminante.setFrameActual((byte) (caminante.getFrameActual() + 1));
                }
                break;
            case Caminante.GRUNIENDO:
                if (caminante.getFrameActual() < 17) {
                    caminante.setFrameActual((byte) (caminante.getFrameActual() + 1));
                } else {
                    caminante.setEstadoActual(Caminante.ATACANDO);
                }
                break;
        }
    }

    @Override
    public void moverEnDireccion(Enemigo caminante) {

        Caminante caminanteEnemy = (Caminante) caminante;
        caminanteEnemy.setDireccionX((int) (Math.random() * 9) - 4);
        if (Math.abs(caminanteEnemy.getDireccionX()) < 4) {
            caminanteEnemy.setDireccionY(4 - Math.abs(caminanteEnemy.getDireccionX()));
        } else {
            caminanteEnemy.setDireccionY(2);
        }
    }

    public void terminaDeAtacar() {
        caminante.setEstadoActual(Caminante.GRUNIENDO);
    }
}
