package edu.puj.pattern_design.zombie_killer.service.zombies;

import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_RANGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.DERROTADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.LENTITUD_BOSS;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_INICIAL;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.SALUD_BOSS;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.VOLANDO;

@Getter
@Setter
public class Boss extends Enemy implements ZigzagMoving {


    /**
     * valor numerico entero que representa la direccion o velocidad en el eje X
     */
    private int directionX;
    /**
     * valor numerico entero que representa la direccion o velocidad en el eje Y
     */
    private int directionY;
    /**
     * valor que representa la posicion en el eje X del jefe
     */
    private int posHorizontal;

    /**
     * Constructor del jefe al iniciar la ronda 10
     */
    public Boss() {
        super();
        setEstadoActual(VOLANDO);
        setHealth(SALUD_BOSS);
        setLentitud(LENTITUD_BOSS);
    }

    /**
     * Constructor del jefe al cargar la partida si fue guardada en la ronda del
     * jefe
     *
     * @param salud
     */
    public Boss(byte salud) {
        super();
        setEstadoActual(VOLANDO);
        setHealth(salud);
        setLentitud(LENTITUD_BOSS);
        moverEnDireccion();
    }

    public void terminaDeAtacar() {
        setEstadoActual(VOLANDO);
        setPosY(POS_INICIAL);
        moverEnDireccion();
        posHorizontal = posAleatoriaX();
    }

    public void moverEnDireccion() {
        directionX = (int) (Math.random() * 13) - 6;

        if (directionX > 0 && directionX < 6) {
            directionY = 6 - directionX;
        } else if (directionX <= 0 && directionX > -6) {
            directionY = 6 + directionX;
        } else {
            directionY = 2;
        }
    }

    @Override
    public boolean checkShoot(int x, int y, int danio) {
        boolean leDio = false;
        int danioResultante = danio;

        if (x > posHorizontal + 108 && x < posHorizontal + 160 && y > getPosY() + 110 && y < getPosY() + 190) {
            if (danio == REMINGTON_DAMAGE) {
                danioResultante = danioResultante - (POS_ATAQUE - getPosY()) / REMINGTON_RANGE;
            }

            setHealth((byte) (getHealth() - danioResultante));
            terminaDeAtacar();

            if (getHealth() <= 0) {
                setEstadoActual(DERROTADO);
                posHorizontal = 365;
                setPosY(POS_INICIAL);
            }

            leDio = true;
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

    @Override
    public int getPosX() {
        return posHorizontal;
    }

    public void setPosX(int posHorizontal) {
        this.posHorizontal = posHorizontal;
    }

    @Override
    public int getDirectionX() {
        return directionX;
    }

    @Override
    public int getDirectionY() {
        return directionY;
    }

}
