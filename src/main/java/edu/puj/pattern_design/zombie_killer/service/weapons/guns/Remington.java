package edu.puj.pattern_design.zombie_killer.service.weapons.guns;

import edu.puj.pattern_design.zombie_killer.service.weapons.guns.ammunition.Ammunition;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_BULLETS;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_RECHARGE_TIME;

public class Remington extends GunWeapon {

    private static final long serialVersionUID = 1L;

    private static Remington remington;

    private Remington() {
        super();
        setBackward(REMINGTON_INITIAL_RECHARGE_TIME);
    }

    public static Remington getInstance() {
        if (remington == null) {
            remington = new Remington();
        }

        Ammunition ammunition = new Ammunition(REMINGTON_INITIAL_BACKWARD,
                REMINGTON_INITIAL_BULLETS, REMINGTON_DAMAGE);
        remington.setAmmunition(ammunition);
        return remington;
    }
}
