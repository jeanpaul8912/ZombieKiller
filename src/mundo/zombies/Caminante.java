package mundo.zombies;

import mundo.weapons.guns.Remington;

import static mundo.constants.ZombieKillerConstants.REMINGTON_DAMAGE;

public class Caminante extends Zombie implements SeMueveEnZigzag {

    public static final String GRUNIENDO = "gruniendo";

    private int direccionX;

    private int direccionY;

    /**
     * Constructor del zombie caminante con sus caracteristicas con corde a la ronda
     *
     * @param ronda
     * @param siguiente
     */
    public Caminante(short ronda, Zombie siguiente) {
        super(ronda, siguiente);
        //moverEnDireccion();
        this.posX = posAleatoriaX();
    }

    /**
     * Constructor que carga las caracteristicas que se guardaron en texto plano
     *
     * @param posX
     * @param posY
     * @param direccionX
     * @param direccionY
     * @param estadoActual
     * @param frameActual
     * @param salud
     * @param ronda
     */
    public Caminante(int posX, int posY, int direccionX, int direccionY, String estadoActual, byte frameActual,
                     byte salud, int ronda) {
        super(posY, estadoActual, frameActual, salud, ronda);
        this.posX = posX;
        this.direccionX = direccionX;
        this.direccionY = direccionY;
    }

    @Override
    public int getDireccionX() {
        return direccionX;
    }

    @Override
    public int getDireccionY() {
        return direccionY;
    }

    /**
     * Constructor del zombie nodo inicializable en caminante
     */
    public Caminante() {
        super();
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

    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    public void setDireccionY(int direccionY) {
        this.direccionY = direccionY;
    }

}