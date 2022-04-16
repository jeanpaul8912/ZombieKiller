package edu.puj.pattern_design.zombie_killer.service.zombies;

import lombok.Getter;
import lombok.Setter;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_INICIAL;

@Getter
@Setter
public abstract class Enemy implements LivingBeing, Cloneable {

    /**
     * todo enemigo tiene una posicion en el eje Y
     */
    private int posY;
    /**
     * lentitud representativa con corde al nivel del enemigo
     */
    private short lentitud;
    /**
     * numero representativo de la imagen actual con corde al estado
     */
    private byte frameActual;
    /**
     * cadena de caracteres que representa el estado actual del enemigo
     */
    private String estadoActual;
    /**
     * numero que representa la salud del enemigo
     */
    private byte salud;

    /**
     * constructor basico que inicializa unicamente la posicion en Y
     */
    public Enemy() {
        posY = POS_INICIAL;
    }

    /**
     * Constructor compuesto para cargar informacion
     *
     * @param posY
     * @param estadoActual
     * @param frameActual
     */
    public Enemy(int posY, String estadoActual, byte frameActual) {
        this.posY = posY;
        this.estadoActual = estadoActual;
        this.frameActual = frameActual;
    }

    /**
     * cambia el estado actual del enemigo
     *
     * @param estadoActual
     */
    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
        frameActual = 0;
    }

    /**
     * obtiene la posicion X del enemigo
     *
     * @return posicionX
     */
    public abstract int getPosX();

    /**
     * crea una posicion aleatoria en el eje X para la aparicion del enemigo
     *
     * @return posAleatoria
     */
    public short posAleatoriaX() {
        int posAleatoria = (int) (Math.random() * ANCHO_PANTALLA / 3) + ANCHO_PANTALLA / 3 - 75;
        return (short) posAleatoria;
    }

    /**
     * ejecuta cierta accion al terminar de atacar al personaje
     */
    //public abstract void terminaDeAtacar();

    /**
     * comprueba que las posiciones de la bala coincidan con la posicion del enemigo
     * y reduzca su salud en caso de ser afectado
     *
     * @param x
     * @param y
     * @param danio
     * @return true si fue afectado por el disparo
     */
    public abstract boolean checkShoot(int x, int y, int danio);

    /**
     * obtiene la ruta de la imagen desde la carpeta img
     *
     * @return
     */
    public abstract String getURL(int level);

    public Enemy cloneEnemy() {
        try {
            return (Enemy) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException.getMessage());
        }
    }


    /**
     * obtiene la posicion Y del enemigo
     *
     * @return posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * cambia la posicion en el eje Y
     *
     * @param posY
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * obtiene la lentitud del enemigo (tiempo de espera en milisegundos por cada
     * movimiento)
     *
     * @return lentitud
     */
    public short getLentitud() {
        return lentitud;
    }

    /**
     * cambia la lentitud del enemigo
     *
     * @param lentitud
     */
    public void setLentitud(short lentitud) {
        this.lentitud = lentitud;
    }

    @Override
    public byte getHealth() {
        return salud;
    }

    @Override
    public void setHealth(byte newHealth) {
        this.salud = newHealth;
    }

}