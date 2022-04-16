package edu.puj.pattern_design.zombie_killer.service.zombies;

import java.util.Formatter;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_RANGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;

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
                    resultDamage = resultDamage - (POS_ATAQUE - getPosY()) / REMINGTON_RANGE;
                }

                setHealth((byte) (getHealth() - resultDamage));

                if (getHealth() <= 0) {
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
