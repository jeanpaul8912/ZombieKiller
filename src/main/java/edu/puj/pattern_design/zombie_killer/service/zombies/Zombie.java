package edu.puj.pattern_design.zombie_killer.service.zombies;

import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.CAMINANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.LENTITUD1;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.LENTITUD2;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.LENTITUD3;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.LENTITUD4;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.SALUD1;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.SALUD2;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.SALUD3;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.SALUD4;

public abstract class Zombie extends Enemy {

    /**
     * zombie que se encuentra al frente o fue generado antes que este
     */
    private Zombie alFrente;
    /**
     * zombie que se encuentra atras o fue generado despues que este
     */
    private Zombie atras;

    public int posX;

    protected int direccionX;

    protected int direccionY;

    /**
     * Constructor de un zombie nodo
     */
    protected Zombie() {
        setEstadoActual(NODO);
    }

    protected Zombie(int posY, String estadoActual, byte frameActual, byte salud, int ronda) {
        super(posY, estadoActual, frameActual);
        determinarDificultadZombie(ronda);
        setHealth(salud);
    }

    protected Zombie(short nivel, Zombie atras) {
        determinarDificultadZombie(nivel);
        setEstadoActual(CAMINANDO);
        this.atras = atras;
    }

    /**
     * metodo auxiliar que determina la dificultad de un zombie cuando se crea o se
     * carga
     *
     * @param ronda
     */
    public void determinarDificultadZombie(int ronda) {
        switch (ronda) {
            case 9:
            case 8:
                setLentitud(LENTITUD3);
                setHealth(SALUD4);
                break;
            case 7:
                setLentitud(LENTITUD4);
                setHealth(SALUD2);
                break;
            case 6:
                setLentitud(LENTITUD3);
                setHealth(SALUD3);
                break;
            case 5:
            case 4:
                setLentitud(LENTITUD3);
                setHealth(SALUD2);
                break;
            case 3:
                setLentitud(LENTITUD2);
                setHealth(SALUD2);
                break;
            case 2:
                setLentitud(LENTITUD1);
                setHealth(SALUD2);
                break;
            case 1:
            default:
                setLentitud(LENTITUD1);
                setHealth(SALUD1);
                break;
        }
    }

    /**
     * obtiene el zombie que fue creado antes que el correspondiente
     *
     * @return
     */
    public Zombie getAlFrente() {
        return alFrente;
    }

    /**
     * Cambia el zombie que se encuentra al frente del correspondiente
     *
     * @param alFrente
     */
    public void setAlFrente(Zombie alFrente) {
        this.alFrente = alFrente;
    }

    /**
     * obtiene el zombie que fue creado despues del correspondiente
     *
     * @return zombie de atras
     */
    public Zombie getAtras() {
        return atras;
    }

    /**
     * Cambia el zombie que se encuentra al atras del correspondiente
     *
     * @param atras
     */
    public void setAtras(Zombie atras) {
        this.atras = atras;
    }

    /**
     * se elimina a si mismo cambiando las asociaciones de los zombies laterales
     */
    public void eliminarse() {
        atras.alFrente = alFrente;
        alFrente.atras = atras;
    }

    @Override
    public abstract boolean comprobarDisparo(int x, int y, int damage);

    /**
     * entra en la lista enlazada relacionando los parametros zombie atras y al
     * frente
     *
     * @param zombAlFrente
     * @param zombAtras
     */
    public void introducirse(Zombie zombAlFrente, Zombie zombAtras) {
        atras = zombAtras;
        alFrente = zombAlFrente;
        zombAlFrente.atras = this;
        zombAtras.alFrente = this;
    }

    public void inicializar(short nivel, Zombie atras) {
        determinarDificultadZombie(nivel);
        setEstadoActual(CAMINANDO);
        setPosX();
        this.atras = atras;
    }

    public void setPosX() {
        this.posX = posAleatoriaX();
    }

    public void setPosXForClone(int posX) {
        this.posX = posX;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    public void setDireccionY(int direccionY) {
        this.direccionY = direccionY;
    }

}