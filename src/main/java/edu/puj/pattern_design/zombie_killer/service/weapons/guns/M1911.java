package edu.puj.pattern_design.zombie_killer.service.weapons.guns;

import edu.puj.pattern_design.zombie_killer.service.weapons.guns.ammunition.Ammunition;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_INITIAL_BULLETS;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_INITIAL_RECHARGE_TIME;

public class M1911 extends GunWeapon {

    private static final long serialVersionUID = 1L;

    private static M1911 m1911;

    /**
     * Constructor del arma M1911 con sus caracteristicas
     */
    private M1911() {
        super();
        setRetroceso(M1911_INITIAL_RECHARGE_TIME);
    }

    public static M1911 getInstance() {
        if (m1911 == null) {
            m1911 = new M1911();
        }

        Ammunition ammunition = new Ammunition(M1911_INITIAL_BACKWARD,
                M1911_INITIAL_BULLETS, M1911_DAMAGE);
        m1911.setAmmunition(ammunition);
        return m1911;
    }

}
