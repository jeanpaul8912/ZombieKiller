package mundo.weapons.guns.ammunition;

import java.io.Serializable;

public class Ammunition implements Serializable {

    private static final long serialVersionUID = 9091188907527072741L;

    protected int rechargeTime;

    protected int availableBullets;

    protected int maxBullets;

    protected int damage;

    public Ammunition(int rechargeTime, int maxBullets, int damage) {
        this.rechargeTime = rechargeTime;
        this.availableBullets = maxBullets;
        this.maxBullets = maxBullets;
        this.damage = damage;
    }

    public int getRechargeTime() {
        return rechargeTime;
    }

    public int getAvailableBullets() {
        return availableBullets;
    }

    public void setAvailableBullets(int availableBullets) {
        this.availableBullets = Math.max(availableBullets, 0);
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    public int getDamage() {
        return damage;
    }

    public void reload() {
        this.availableBullets = maxBullets;
    }

    public void improveAmmunition() {
        maxBullets += 2;
        availableBullets = maxBullets;
        damage++;
    }

}
