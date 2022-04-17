package edu.puj.pattern_design.zombie_killer.service.zombies;

import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
public abstract class Zombie extends Enemy {

    /**
     * zombie que se encuentra al frente o fue generado antes que este
     */
    private Zombie inFront;
    /**
     * zombie que se encuentra atras o fue generado despues que este
     */
    private Zombie inBack;

    public int posX;

    protected int directionX;

    protected int directionY;

    /**
     * Constructor de un zombie nodo
     */
    protected Zombie() {
        setCurrentStatus(NODO);
    }

    protected Zombie(int posY, String estadoActual, byte frameActual, byte salud, int ronda) {
        super(posY, estadoActual, frameActual);
        determinarDificultadZombie(ronda);
        setHealth(salud);
    }

    protected Zombie(short nivel, Zombie inBack) {
        determinarDificultadZombie(nivel);
        setCurrentStatus(CAMINANDO);
        this.inBack = inBack;
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
                setSpeed(LENTITUD3);
                setHealth(SALUD4);
                break;
            case 7:
                setSpeed(LENTITUD4);
                setHealth(SALUD2);
                break;
            case 6:
                setSpeed(LENTITUD3);
                setHealth(SALUD3);
                break;
            case 5:
            case 4:
                setSpeed(LENTITUD3);
                setHealth(SALUD2);
                break;
            case 3:
                setSpeed(LENTITUD2);
                setHealth(SALUD2);
                break;
            case 2:
                setSpeed(LENTITUD1);
                setHealth(SALUD2);
                break;
            case 1:
            default:
                setSpeed(LENTITUD1);
                setHealth(SALUD1);
                break;
        }
    }

    /**
     * se elimina a si mismo cambiando las asociaciones de los zombies laterales
     */
    public void eliminate() {
        inBack.inFront = inFront;
        inFront.inBack = inBack;
    }

    @Override
    public abstract boolean checkShoot(int x, int y, int damage);

    /**
     * entra en la lista enlazada relacionando los parametros zombie atras y al
     * frente
     *
     * @param zombAlFrente
     * @param zombAtras
     */
    public void introduce(Zombie zombAlFrente, Zombie zombAtras) {
        inBack = zombAtras;
        inFront = zombAlFrente;
        zombAlFrente.inBack = this;
        zombAtras.inFront = this;
    }

    public void inicialize(short nivel, Zombie atras) {
        determinarDificultadZombie(nivel);
        setCurrentStatus(CAMINANDO);
        setPosX();
        this.inBack = atras;
    }

    public void setPosX() {
        this.posX = aleatoryPositionX();
    }

}