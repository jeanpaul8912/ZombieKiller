package edu.puj.pattern_design.zombie_killer.service.weapons;

import edu.puj.pattern_design.zombie_killer.service.weapons.guns.Grenade;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.M1911;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.Remington;
import edu.puj.pattern_design.zombie_killer.service.weapons.whites.Knife;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_GRENADE_TYPE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_KNIFE_TYPE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_M1911_TYPE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.WEAPON_REMINGTON_TYPE;

public class WeaponFactoryImpl implements WeaponFactory {

    @Override
    public Weapon createWeapon(String typeWeapon) {
        if (typeWeapon.equalsIgnoreCase(WEAPON_GRENADE_TYPE)) {
            return Grenade.getInstance();
        } else if (typeWeapon.equalsIgnoreCase(WEAPON_REMINGTON_TYPE)) {
            return Remington.getInstance();
        } else if (typeWeapon.equalsIgnoreCase(WEAPON_M1911_TYPE)) {
            return M1911.getInstance();
        } else if (typeWeapon.equalsIgnoreCase(WEAPON_KNIFE_TYPE)) {
            return Knife.getInstance();
        } else {
            throw new IllegalStateException("Unexpected value: " + typeWeapon);
        }
    }
}