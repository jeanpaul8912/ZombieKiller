package edu.puj.pattern_design.zombie_killer.service.weapons.guns;

import edu.puj.pattern_design.zombie_killer.service.weapons.Weapon;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.ammunition.Ammunition;
import lombok.Getter;
import lombok.Setter;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.RECHARGING;

@Getter
@Setter
public abstract class GunWeapon extends Weapon {

    private static final long serialVersionUID = 1L;

    private boolean blooded;

    protected Ammunition ammunition;

    protected GunWeapon() {
    }

    @Override
    public long calculatePause() {
        long pause = 0;

        if (getStatus().equals(RECHARGING)) {
            pause = ammunition.getRechargeTime();
        } else if (getStatus().equals(CARGANDO)) {
            pause = getBackward();
        }

        return pause;
    }

    public void shoot() {
        ammunition.setAvailableBullets(ammunition.getAvailableBullets() - 1);
    }

    public void reload() {
        ammunition.reload();
    }

    public int getMaxBullets() {
        return ammunition.getMaxBullets();
    }

    public int getAvailableBullets() {
        return ammunition.getAvailableBullets();
    }

    public void improveGun() {
        ammunition.improveAmmunition();
    }

    @Override
    public int getDamage() {
        return ammunition.getDamage();
    }

}
