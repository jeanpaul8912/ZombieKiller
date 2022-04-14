package mundo.zombies;

import mundo.weapons.guns.Remington;

import static mundo.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static mundo.constants.ZombiesConstants.MURIENDO;
import static mundo.constants.ZombiesConstants.POS_ATAQUE;

public abstract class ZombieZigZag extends Zombie implements SeMueveEnZigzag {

    private int direccionX;

    private int direccionY;

    public ZombieZigZag() {

    }

    public ZombieZigZag(short round, Zombie next) {
        super(round, next);
        this.posX = posAleatoriaX();
    }

    public ZombieZigZag(int posX, int posY, int direccionX, int direccionY, String estadoActual,
                        byte frameActual, byte salud, int ronda) {
        super(posY, estadoActual, frameActual, salud, ronda);
        this.posX = posX;
        this.direccionX = direccionX;
        this.direccionY = direccionY;
    }

    @Override
    public boolean comprobarDisparo(int x, int y, int damage) {
        boolean leDio = false;
        int resultDamage = damage;

        if (x > posX + 36 && x < posX + 118 && y > getPosY() + 5 && y < getPosY() + 188) {
            if (!getEstadoActual().equals(MURIENDO)) {
                if (y < getPosY() + 54) {
                    resultDamage = ((byte) (damage + 2));
                }

                // el 320 define la distancia entre el zombie y el personaje
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

    public abstract String getImageUrl();

    @Override
    public int getDireccionX() {
        return direccionX;
    }

    @Override
    public int getDireccionY() {
        return direccionY;
    }

    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    public void setDireccionY(int direccionY) {
        this.direccionY = direccionY;
    }

}
