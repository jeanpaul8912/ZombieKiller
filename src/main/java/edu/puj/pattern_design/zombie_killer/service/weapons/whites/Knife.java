package edu.puj.pattern_design.zombie_killer.service.weapons.whites;

import edu.puj.pattern_design.zombie_killer.service.weapons.Weapon;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.KNIFE_DAMAGE;

public class Knife extends Weapon {

    private static final long serialVersionUID = 1L;

    private static Knife knife;

    public Knife() {
        setRetroceso(200);
        setDamage(KNIFE_DAMAGE);
    }

    public static Knife getInstance() {
        if (knife == null) {
            knife = new Knife();
        }

        return knife;
    }

}