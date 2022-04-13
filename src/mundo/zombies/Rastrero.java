package mundo.zombies;

import mundo.weapons.guns.Remington;

import static mundo.constants.ZombieKillerConstants.REMINGTON_DAMAGE;

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
    public boolean comprobarDisparo(int x, int y, int danio) {
        boolean leDio = false;
        int danioResultante = danio;
        if (!getEstadoActual().equals(MURIENDO)) {
            if (x > posX + 36 && x < posX + 118 && y > getPosY() + 120 && y < getPosY() + 196) {
                // comprueba headshot
                if (y < getPosY() + 162)
                    danioResultante = ((byte) (danio + 2));
                if (danio == REMINGTON_DAMAGE) {
                    danioResultante = danioResultante - (POS_ATAQUE - getPosY()) / Remington.RANGO;
                }
                setSalud((byte) (getSalud() - danioResultante));
                if (getSalud() <= 0) {
                    setEstadoActual(MURIENDO);
                }
                leDio = true;
            }
        }

        return leDio;
    }

}
