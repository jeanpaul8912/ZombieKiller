package edu.puj.pattern_design.zombie_killer.service.zombies;

import lombok.Getter;
import lombok.Setter;

import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_INICIAL;

@Getter
@Setter
public abstract class Enemy implements LivingBeing, Cloneable {

    private int posY;

    private short speed;

    private byte currentFrame;

    private String currentStatus;

    private byte health;

    protected Enemy() {
        posY = POS_INICIAL;
    }

    protected Enemy(int posY, String currentStatus, byte currentFrame) {
        this.posY = posY;
        this.currentStatus = currentStatus;
        this.currentFrame = currentFrame;
    }

    public void setCurrentStatus(String estadoActual) {
        this.currentStatus = estadoActual;
        currentFrame = 0;
    }

    public abstract int getPosX();

    public short aleatoryPositionX() {
        int aleatoryPosition = (int) (Math.random() * ANCHO_PANTALLA / 3) + ANCHO_PANTALLA / 3 - 75;
        return (short) aleatoryPosition;
    }

    public abstract boolean checkShoot(int x, int y, int damage);

    public abstract String getURL(int level);

    public Enemy cloneEnemy() {
        try {
            return (Enemy) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException.getMessage());
        }
    }

    @Override
    public byte getHealth() {
        return health;
    }

    @Override
    public void setHealth(byte newHealth) {
        this.health = newHealth;
    }

}