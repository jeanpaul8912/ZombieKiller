package edu.puj.pattern_design.zombie_killer.service.zombies;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_RANGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;

public abstract class ZombieZigZag extends Zombie implements ZigzagMoving {

    private int direccionX;

    private int direccionY;

    public ZombieZigZag() {

    }

    public ZombieZigZag(int posX, int posY, int direccionX, int direccionY, String estadoActual,
                        byte frameActual, byte salud, int ronda) {
        super(posY, estadoActual, frameActual, salud, ronda);
        this.posX = posX;
        this.direccionX = direccionX;
        this.direccionY = direccionY;
    }

    @Override
    public boolean checkShoot(int x, int y, int damage) {
        boolean leDio = false;
        int resultDamage = damage;

        if (x > posX + 36 && x < posX + 118 && y > getPosY() + 5 && y < getPosY() + 188) {
            if (!getCurrentStatus().equals(MURIENDO)) {
                if (y < getPosY() + 54) {
                    resultDamage = ((byte) (damage + 2));
                }

                // el 320 define la distancia entre el zombie y el personaje
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
    public int getDirectionX() {
        return direccionX;
    }

    @Override
    public int getDirectionY() {
        return direccionY;
    }

    public void setDirectionX(int directionX) {
        this.direccionX = directionX;
    }

    public void setDirectionY(int directionY) {
        this.direccionY = directionY;
    }

}
