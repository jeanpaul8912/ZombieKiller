package edu.puj.pattern_design.zombie_killer.service.zombies;

import java.util.Formatter;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_RANGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;

public class DragZombie extends Zombie {

    public DragZombie(short ronda, Zombie siguiente) {
        super(ronda, siguiente);
    }

    public DragZombie(int posX, int posY, String estadoActual, byte frameActual, byte salud, int ronda) {
        super(posY, estadoActual, frameActual, salud, ronda);
        this.posX = posX;
    }

    @Override
    public boolean checkShoot(int x, int y, int damage) {
        boolean leDio = false;
        int resultDamage = damage;

        if (!getCurrentStatus().equals(MURIENDO)) {
            if (x > posX + 36 && x < posX + 118 && y > getPosY() + 120 && y < getPosY() + 196) {
                if (y < getPosY() + 162) {
                    resultDamage = ((byte) (damage + 2));
                }

                if (damage == REMINGTON_DAMAGE) {
                    resultDamage = resultDamage - (POS_ATAQUE - getPosY()) / REMINGTON_RANGE;
                }

                setHealth((byte) (getHealth() - resultDamage));

                if (getHealth() <= 0) {
                    setCurrentStatus(MURIENDO);
                }

                leDio = true;
            }
        }

        return leDio;
    }

    @Override
    public String getURL(int level) {
        try (Formatter formatter = new Formatter()) {
            return "/img/" + getClass().getSimpleName() + "/" + getCurrentStatus() + "/"
                    + formatter.format("%02d", getCurrentFrame()) + ".png";
        }
    }

}
