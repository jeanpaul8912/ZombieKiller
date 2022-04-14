package mundo.weapons.guns;

import mundo.weapons.guns.ammunition.Ammunition;
import mundo.weapons.guns.ammunition.GrenadeAmmunition;

import static mundo.constants.WeaponsConstants.GREANDE_INITIAL_RECHARGE_TIME;
import static mundo.constants.WeaponsConstants.GRENADE_DAMAGE;
import static mundo.constants.WeaponsConstants.GRENADE_INITIAL_BULLETS;

public class Grenade extends GunWeapon {

    private static final long serialVersionUID = 1L;

    private static Grenade grenade;

    /**
     * Constructor de la granada con su respectiva cantidad y danio
     */
    private Grenade() {
        super();
        setRetroceso(0);
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
