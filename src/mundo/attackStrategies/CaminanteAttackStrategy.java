package mundo.attackStrategies;

import mundo.zombies.Caminante;
import mundo.zombies.Enemigo;

import static mundo.constants.CampConstants.ANCHO_PANTALLA;
import static mundo.constants.ZombiesConstants.ANCHO_IMAGEN;
import static mundo.constants.ZombiesConstants.ATACANDO;
import static mundo.constants.ZombiesConstants.CAMINANDO;
import static mundo.constants.ZombiesConstants.GRUNIENDO;
import static mundo.constants.ZombiesConstants.MURIENDO;
import static mundo.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static mundo.constants.ZombiesConstants.POS_ATAQUE;

public class CaminanteAttackStrategy extends AttackStrategy implements IAttackMovement {

    private Caminante caminante;

    @Override
    public void executeAttack(Enemigo enemy) {
        caminante = (Caminante) enemy;
        attack(caminante);
    }

    public void attack(Caminante caminante) {
        switch (caminante.getEstadoActual()) {
            case CAMINANDO:
                if (caminante.getPosY() > POS_ATAQUE) {
                    caminante.setEstadoActual(ATACANDO);
                } else {
                    if (caminante.getPosX() > ANCHO_PANTALLA - ANCHO_IMAGEN || caminante.getPosX() < 0) {
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
            case ATACANDO:
                if (caminante.getFrameActual() < 16) {
                    caminante.setFrameActual((byte) (caminante.getFrameActual() + 1));
                }
                break;
            case MURIENDO:
            case MURIENDO_INCENDIADO:
                if (caminante.getFrameActual() < 17) {
                    caminante.setFrameActual((byte) (caminante.getFrameActual() + 1));
                }
                break;
            case GRUNIENDO:
                if (caminante.getFrameActual() < 17) {
                    caminante.setFrameActual((byte) (caminante.getFrameActual() + 1));
                } else {
                    caminante.setEstadoActual(ATACANDO);
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
        caminante.setEstadoActual(GRUNIENDO);
    }
}
