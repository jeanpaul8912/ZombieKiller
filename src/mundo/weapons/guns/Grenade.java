package mundo.weapons.guns;

import mundo.weapons.guns.ammunition.Ammunition;
import mundo.weapons.guns.ammunition.GrenadeAmmunition;

import static mundo.constants.ZombieKillerConstants.GREANDE_INITIAL_RECHARGE_TIME;
import static mundo.constants.ZombieKillerConstants.GRENADE_DAMAGE;
import static mundo.constants.ZombieKillerConstants.GRENADE_INITIAL_BULLETS;

public class Grenade extends WeaponDeFuego {

	private static final long serialVersionUID = 1L;

	private static Grenade grenade;

	/**
	 * Constructor de la granada con su respectiva cantidad y danio
	 */
	private Grenade(Ammunition ammunition) {
		super();
		setRetroceso(0);
		this.ammunition = ammunition;
	}

	public static Grenade getInstance() {
		if (grenade == null) {
			Ammunition ammunition = new GrenadeAmmunition(GREANDE_INITIAL_RECHARGE_TIME,
					GRENADE_INITIAL_BULLETS, GRENADE_DAMAGE);
			grenade = new Grenade(ammunition);
		}

		return grenade;
	}

	@Override
	public void improveGun() {
		ammunition.improveAmmunition();
	}

}
