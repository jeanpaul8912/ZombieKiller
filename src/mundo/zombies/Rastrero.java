package mundo.zombies;

import mundo.weapons.guns.Remington;

import java.util.Formatter;

import static mundo.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static mundo.constants.ZombiesConstants.MURIENDO;
import static mundo.constants.ZombiesConstants.POS_ATAQUE;

public class Rastrero extends Zombie {

    /**
     * Constructor del zombie rastrero con sus caracteristicas con corde a la ronda
     *
     * @param ronda
     * @param siguiente
     */
    public Rastrero(short ronda, Zombie siguiente) {
        super(ronda, siguiente);
    }

    /**
     * Constructor que carga las caracteristicas que se guardaron en texto plano
     *
     * @param posX
     * @param posY
     * @param estadoActual
     * @param frameActual
     * @param salud
     * @param ronda
     */
    public Rastrero(int posX, int posY, String estadoActual, byte frameActual, byte salud, int ronda) {
        super(posY, estadoActual, frameActual, salud, ronda);
        this.posX = posX;
    }

    @Override
    public boolean comprobarDisparo(int x, int y, int damage) {
        boolean leDio = false;
        int resultDamage = damage;

        if (!getEstadoActual().equals(MURIENDO)) {
            if (x > posX + 36 && x < posX + 118 && y > getPosY() + 120 && y < getPosY() + 196) {
                if (y < getPosY() + 162) {
                    resultDamage = ((byte) (damage + 2));
                }

                if (damage == REMINGTON_DAMAGE) {
                    resultDamage = resultDamage - (POS_ATAQUE - getPosY()) / Remington.RANGO;
                }

                setSalud((byte) (getSalud() - resultDamage));

                if (getSalud() <= 0) {
                    setEstadoActual(MURIENDO);
                }

                leDio = true;
            }
        }

        return leDio;
    }

    @Override
    public String getURL(int level) {
        try (Formatter formatter = new Formatter()) {
            return "/img/" + getClass().getSimpleName() + "/" + getEstadoActual() + "/"
                    + formatter.format("%02d", getFrameActual()) + ".png";
        }
    }

}
