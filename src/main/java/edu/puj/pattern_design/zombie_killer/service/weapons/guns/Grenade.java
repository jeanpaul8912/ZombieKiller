package edu.puj.pattern_design.zombie_killer.service.weapons.guns;

import edu.puj.pattern_design.zombie_killer.service.weapons.guns.ammunition.Ammunition;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.ammunition.GrenadeAmmunition;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.GREANDE_INITIAL_RECHARGE_TIME;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.GRENADE_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.GRENADE_INITIAL_BULLETS;

public class Grenade extends GunWeapon {

    private static final long serialVersionUID = 1L;

    private static Grenade grenade;

    private Grenade() {
        super();
        setBackward(0);
    }

    public static Grenade getInstance() {
        if (grenade == null) {
            grenade = new Grenade();
        }

        Ammunition ammunition = new GrenadeAmmunition(GREANDE_INITIAL_RECHARGE_TIME,
                GRENADE_INITIAL_BULLETS, GRENADE_DAMAGE);
        grenade.setAmmunition(ammunition);
        return grenade;
    }

    @Override
    public void improveGun() {
        ammunition.improveAmmunition();
    }

}
