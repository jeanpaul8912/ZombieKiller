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

    private int directionX;

    private int directionY;

    private int posHorizontal;

    public Boss() {
        super();
        setCurrentStatus(VOLANDO);
        setHealth(SALUD_BOSS);
        setSpeed(LENTITUD_BOSS);
    }

    public Boss(byte salud) {
        super();
        setCurrentStatus(VOLANDO);
        setHealth(salud);
        setSpeed(LENTITUD_BOSS);
        moverEnDireccion();
    }

    public void terminaDeAtacar() {
        setCurrentStatus(VOLANDO);
        setPosY(POS_INICIAL);
        moverEnDireccion();
        posHorizontal = aleatoryPositionX();
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
    public boolean checkShoot(int x, int y, int damage) {
        boolean leDio = false;
        int danioResultante = damage;

        if (x > posHorizontal + 108 && x < posHorizontal + 160 && y > getPosY() + 110 && y < getPosY() + 190) {
            if (damage == REMINGTON_DAMAGE) {
                danioResultante = danioResultante - (POS_ATAQUE - getPosY()) / REMINGTON_RANGE;
            }

            setHealth((byte) (getHealth() - danioResultante));
            terminaDeAtacar();

            if (getHealth() <= 0) {
                setCurrentStatus(DERROTADO);
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
            return "/img/" + getClass().getSimpleName() + "/" + getCurrentStatus() + "/"
                    + formatter.format("%02d", getCurrentFrame()) + ".png";
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
