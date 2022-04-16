package edu.puj.pattern_design.zombie_killer.service.weapons.guns.ammunition;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.IMPROVE_RECHARGE_TIME;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.MINIMUM_RECHARGE_TIME;

@Getter
@Setter
public class Ammunition implements Serializable {

    private static final long serialVersionUID = 9091188907527072741L;

    protected int rechargeTime;

    protected int availableBullets;

    protected int maxBullets;

    protected byte damage;

    public Ammunition(int rechargeTime, int maxBullets, byte damage) {
        this.rechargeTime = rechargeTime;
        this.availableBullets = maxBullets;
        this.maxBullets = maxBullets;
        this.damage = damage;
    }

    public void setAvailableBullets(int availableBullets) {
        this.availableBullets = Math.max(availableBullets, 0);
    }


    public void reload() {
        this.availableBullets = maxBullets;
    }

    public void improveAmmunition() {
        maxBullets += 2;
        availableBullets = maxBullets;
        damage++;
        setRechargeTime(IMPROVE_RECHARGE_TIME);
    }

    public void setRechargeTime(int rechargeTimeImprove) {
        this.rechargeTime -= rechargeTimeImprove;

        if (rechargeTime < MINIMUM_RECHARGE_TIME) {
            rechargeTime = MINIMUM_RECHARGE_TIME;
        }
    }

}
